package site.minnan.entry.application.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.TravelerProviderService;
import site.minnan.entry.application.service.TemperatureService;
import site.minnan.entry.domain.aggregate.TemperatureRecord;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.TemperatureRecordMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.userinterface.dto.temperture.GetTemperatureRecordDTO;
import site.minnan.entry.userinterface.dto.temperture.RecordTemperatureDTO;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    private TemperatureRecordMapper temperatureRecordMapper;

    @Autowired
    @Qualifier("BlankFilter")
    private Function<String, Optional<String>> blankFilter;

    @Autowired
    private TravelerProviderService travelerProviderService;

    /**
     * 获取体温记录列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TemperatureRecordVO> getTemperatureRecordList(GetTemperatureRecordDTO dto) {
        QueryWrapper<TemperatureRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.format("datediff(create_time, '{}')", dto.getDate()), 0);
        Optional.ofNullable(dto.getHotelId()).ifPresent(s -> queryWrapper.eq("hotel_id", s));
        blankFilter.apply(dto.getTravelerName()).ifPresent(s -> queryWrapper.eq("traveler_name", s));
        Page<TemperatureRecord> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<TemperatureRecord> page = temperatureRecordMapper.selectPage(queryPage, queryWrapper);
        if (page.getTotal() > 0) {
            List<TemperatureRecordVO> list =
                    page.getRecords().stream().map(TemperatureRecordVO::assemble).collect(Collectors.toList());
            Map<Integer, TemperatureRecordVO> groupByTravelerId = list.stream().collect(Collectors.toMap(TemperatureRecordVO::getTravelerId, e -> e));

            List<Traveler> travelerList = travelerProviderService.getTravelerListByIds(groupByTravelerId.keySet());
            travelerList.forEach(traveler -> groupByTravelerId.get(traveler.getId()).setTravelerInfo(traveler));
            return new ListQueryVO<>(list, page.getTotal());
        } else {
            return new ListQueryVO<>(ListUtil.empty(), 0);
        }
    }

    /**
     * 记录乘客体温
     *
     * @param dtoList
     */
    @Override
    @Transactional
    public void recordTemperature(List<RecordTemperatureDTO> dtoList) {
        Collection<RecordTemperatureDTO> dtos = Assert.notEmpty(dtoList);
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        temperatureRecordMapper.filTemperatureRecord(dtos, time, user.getId(), user.getRealName());
    }
}
