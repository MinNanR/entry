package site.minnan.entry.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 添加车次
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
     * 删除车次
     *
     * @param trainId
     */
    @Override
    public void deleteTrain(Integer trainId) {
        Train train = trainMapper.selectById(trainId);
        if (train == null) {
            throw new EntityNotExistException("车次不存在");
        } else if (!TrainStatus.WAITING.equals(train.getStatus())) {
            throw new UnmodifiableException("车次已出发");
        }
        int travelerCount = travelerProviderService.countTravelerOnTrain(trainId);
        if (travelerCount > 0) {
            throw new UnmodifiableException("车次已有乘客登车");
        }
        trainMapper.deleteById(trainId);
    }

    /**
     * 获取车次
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TrainVO> getTrainList(GetTrainListDTO dto) {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        blankFilter.apply(dto.getCarNumber()).ifPresent(s -> queryWrapper.like("car_number", s));
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getTrainList(dto.getCarNumber(), dto.getStart(),
                dto.getPageSize()) : ListUtil.empty();
        List<TrainVO> list = trainList.stream().map(TrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }

    /**
     * 旅客登车
     *
     * @param dto
     */
    @Override
    public void board(BoardDTO dto) {
        Collection<Integer> travelerIds = Assert.notEmpty(dto.getTravelerIds());
        Train train = trainMapper.selectById(dto.getTrainId());
        if (train == null) {
            throw new EntityNotExistException("车次不存在");
        }
        travelerProviderService.travelerBoard(travelerIds, train);
    }

    /**
     * 车次发车
     *
     * @param trainId
     */
    @Override
    @Transactional
    public void depart(Integer trainId) {
        Train train = trainMapper.selectById(trainId);
        if (train == null) {
            throw new EntityNotExistException("车次不存在");
        } else if (!TrainStatus.WAITING.equals(train.getStatus())) {
            throw new UnmodifiableException("该车次已发车");
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        train.depart(current);
        trainMapper.updateById(train);
        travelerProviderService.travelerDepart(trainId);
    }

    /**
     * 获取即将抵达的车次列表
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
        List<TrainData> trainList = totalCount > 0 ?
                trainMapper.getNotArrivedTrainList(dto.getCarNumber(), dto.getHotelId(), dto.getStart(), dto.getPageSize())
                : ListUtil.empty();
        List<ArrivingTrainVO> list = trainList.stream().map(ArrivingTrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }

    /**
     * 车次转入
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
            throw new EntityNotExistException(StrUtil.format("车次id【{}】不存在", s));
        }
        if (trainList.size() < trainIds.size()) {
            List<Integer> existIds = trainList.stream().map(Train::getId).collect(Collectors.toList());
            trainIds.removeAll(existIds);
            String s = trainIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            throw new EntityNotExistException(StrUtil.format("车次id【{}}】不存在", s));
        }
        List<Train> notTransportingTrainList = trainList.stream()
                .filter(e -> !TrainStatus.DEPARTED.equals(e.getStatus()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(notTransportingTrainList)) {
            String s = notTransportingTrainList.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.joining(","));
            throw new UnmodifiableException(StrUtil.format("车次id【{}】不在运输状态", s));
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
     * 获取已抵达的车次
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
            queryWrapper.between("arrive_time", dto.getStartTime(), dto.getEndTime());
        }
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getArrivedTrainList(dto) : ListUtil.empty();
        List<ArrivedTrainVO> list = trainList.stream().map(ArrivedTrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }
}
