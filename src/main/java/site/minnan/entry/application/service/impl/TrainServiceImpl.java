package site.minnan.entry.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.TravelerProviderService;
import site.minnan.entry.application.service.TrainService;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.TrainData;
import site.minnan.entry.domain.mapper.TrainMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.ArrivedTrainVO;
import site.minnan.entry.domain.vo.train.ArrivingTrainVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.infrastructure.enumerate.TrainStatus;
import site.minnan.entry.infrastructure.exception.EntityNotExistException;
import site.minnan.entry.infrastructure.exception.UnmodifiableException;
import site.minnan.entry.userinterface.dto.train.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TravelerProviderService travelerProviderService;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    @Qualifier("BlankFilter")
    private Function<String, Optional<String>> blankFilter;

    @Autowired
    @Qualifier("EndOfDayString")
    private Function<String, String> endOfDayString;

    @Autowired
    @Qualifier("EndOfDay")
    private Function<String, DateTime> endOfDay;

    /**
     * ????????????
     *
     * @param dto
     */
    @Override
    public void addTrain(AddTrainDTO dto) {
        Train train = Train.builder()
                .carNumber(dto.getCarNumber())
                .driverName(dto.getDriverName())
                .driverPhone(dto.getDriverPhone())
                .followerName(dto.getFollowerName())
                .followerPhone(dto.getFollowerPhone())
                .portId(dto.getPortId())
                .portName(dto.getPortName())
                .hotelId(dto.getHotelId())
                .hotelName(dto.getHotelName())
                .status(TrainStatus.WAITING)
                .build();
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        train.setCreateUser(user);
        trainMapper.insert(train);
    }

    /**
     * ????????????
     *
     * @param trainId
     */
    @Override
    public void deleteTrain(Integer trainId) {
        Train train = trainMapper.selectById(trainId);
        if (train == null) {
            throw new EntityNotExistException("???????????????");
        } else if (!TrainStatus.WAITING.equals(train.getStatus())) {
            throw new UnmodifiableException("???????????????");
        }
        int travelerCount = travelerProviderService.countTravelerOnTrain(trainId);
        if (travelerCount > 0) {
            throw new UnmodifiableException("????????????????????????");
        }
        trainMapper.deleteById(trainId);
    }

    /**
     * ????????????
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TrainVO> getTrainList(GetTrainListDTO dto) {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        blankFilter.apply(dto.getEndTime()).ifPresent(s -> {
            dto.setEndTime(endOfDayString.apply(s));
        });
        blankFilter.apply(dto.getCarNumber()).ifPresent(s -> queryWrapper.like("car_number", s));
        if (StrUtil.isNotBlank(dto.getStartTime()) && StrUtil.isNotBlank(dto.getEndTime())) {
            queryWrapper.between("create_time", dto.getStartTime(), dto.getEndTime());
        }
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getTrainList(dto) : ListUtil.empty();
        List<TrainVO> list = trainList.stream().map(TrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }

    /**
     * ????????????
     *
     * @param dto
     */
    @Override
    public void board(BoardDTO dto) {
        Collection<Integer> travelerIds = Assert.notEmpty(dto.getTravelerIds());
        Train train = trainMapper.selectById(dto.getTrainId());
        if (train == null) {
            throw new EntityNotExistException("???????????????");
        }
        travelerProviderService.travelerBoard(travelerIds, train);
    }

    /**
     * ????????????
     *
     * @param trainId
     */
    @Override
    @Transactional
    public void depart(Integer trainId) {
        Train train = trainMapper.selectById(trainId);
        if (train == null) {
            throw new EntityNotExistException("???????????????");
        } else if (!TrainStatus.WAITING.equals(train.getStatus())) {
            throw new UnmodifiableException("??????????????????");
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        train.depart(current);
        trainMapper.updateById(train);
        travelerProviderService.travelerDepart(trainId);
    }

    /**
     * ?????????????????????????????????
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<ArrivingTrainVO> getNotArrivedTrainList(GetNotArrivedTrainListDTO dto) {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getHotelId()).ifPresent(s -> queryWrapper.eq("hotel_id", s));
        blankFilter.apply(dto.getCarNumber()).ifPresent(s -> queryWrapper.like("car_number", s));
        queryWrapper.eq("status", TrainStatus.DEPARTED);
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getNotArrivedTrainList(dto.getCarNumber(), dto.getHotelId())
                : ListUtil.empty();
        List<ArrivingTrainVO> list = trainList.stream().map(ArrivingTrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }

    /**
     * ????????????
     *
     * @param dto
     */
    @Override
    @Transactional
    public void acceptTrain(AcceptTrainDTO dto) {
        Collection<Integer> trainIds = Assert.notEmpty(dto.getTrainIds());
        List<Train> trainList = trainMapper.selectBatchIds(trainIds);
        if (CollectionUtil.isEmpty(trainList)) {
            String s = trainIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            throw new EntityNotExistException(StrUtil.format("??????id???{}????????????", s));
        }
        if (trainList.size() < trainIds.size()) {
            List<Integer> existIds = trainList.stream().map(Train::getId).collect(Collectors.toList());
            trainIds.removeAll(existIds);
            String s = trainIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            throw new EntityNotExistException(StrUtil.format("??????id???{}}????????????", s));
        }
        List<Train> notTransportingTrainList = trainList.stream()
                .filter(e -> !TrainStatus.DEPARTED.equals(e.getStatus()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(notTransportingTrainList)) {
            String s = notTransportingTrainList.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.joining(","));
            throw new UnmodifiableException(StrUtil.format("??????id???{}?????????????????????", s));
        }
        UpdateWrapper<Train> updateWrapper = new UpdateWrapper<>();
        Timestamp current = new Timestamp(System.currentTimeMillis());
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        trainList.forEach(e -> e.arrive(current));
        updateWrapper.in("id", trainIds)
                .set("status", TrainStatus.ARRIVED)
                .set("arrive_time", current)
                .set("update_user_id", user.getId())
                .set("update_user_name", user.getRealName())
                .set("update_time", current);
        trainMapper.update(null, updateWrapper);
        travelerProviderService.travelerArrive(trainList);
    }

    /**
     * ????????????????????????
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<ArrivedTrainVO> getArrivedTrainList(GetTrainListDTO dto) {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", TrainStatus.ARRIVED);
        Optional.ofNullable(dto.getHotelId()).ifPresent(s -> queryWrapper.eq("hotel_id", s));
        if (StrUtil.isNotBlank(dto.getStartTime()) && StrUtil.isNotBlank(dto.getEndTime())) {
            DateTime startTime = DateUtil.parseDate(dto.getStartTime());
            String endTime = endOfDayString.apply(dto.getEndTime());
            dto.setEndTime(endTime);
            queryWrapper.between("arrive_time", startTime, endTime);
        }
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getArrivedTrainList(dto) : ListUtil.empty();
        List<ArrivedTrainVO> list = trainList.stream().map(ArrivedTrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }
}
