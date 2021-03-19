package site.minnan.entry.application.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.TravelerProviderService;
import site.minnan.entry.application.service.TrainService;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.TrainData;
import site.minnan.entry.domain.mapper.TrainMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.infrastructure.enumerate.TrainStatus;
import site.minnan.entry.userinterface.dto.train.AddTrainDTO;
import site.minnan.entry.userinterface.dto.train.GetTrainListDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TravelerProviderService travelerProviderService;

    @Autowired
    private TrainMapper trainMapper;

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
     * 获取车次
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TrainVO> getTrainList(GetTrainListDTO dto) {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getCarNumber()).ifPresent(s -> queryWrapper.like("car_number", s));
        Integer totalCount = trainMapper.selectCount(queryWrapper);
        List<TrainData> trainList = totalCount > 0 ? trainMapper.getTrainList(dto.getCarNumber(), dto.getStart(),
                dto.getPageSize()) : ListUtil.empty();
        List<TrainVO> list = trainList.stream().map(TrainVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, totalCount);
    }
}
