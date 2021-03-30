package site.minnan.entry.application.provider.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.TemperatureProviderService;
import site.minnan.entry.domain.aggregate.TemperatureRecord;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.TemperatureRecordMapper;
import site.minnan.entry.domain.mapper.TravelerMapper;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemperatureProviderServiceImpl implements TemperatureProviderService {

    @Autowired
    private TemperatureRecordMapper temperatureRecordMapper;

    @Autowired
    private TravelerMapper travelerMapper;

    /**
     * 创建体温测量记录
     */
    @Override
    @Transactional
    public void createTemperatureRecord() {
        QueryWrapper<Traveler> travelerQueryWrapper = new QueryWrapper<>();
        travelerQueryWrapper.eq("status", TravelerStatus.QUARANTINE);
        List<Traveler> travelerList = travelerMapper.selectList(travelerQueryWrapper);//筛选出正在隔离的旅客
        Map<Integer, Traveler> idTravelerMap = travelerList.stream().collect(Collectors.toMap(Traveler::getId, e -> e));
        List<TemperatureRecord> todayRecord = temperatureRecordMapper.getTodayRecord(DateUtil.today());//获取已有记录的旅客
        List<Integer> travelerHasRecord =
                todayRecord.stream().map(TemperatureRecord::getTravelerId).collect(Collectors.toList());
        travelerHasRecord.forEach(idTravelerMap::remove);//去除已经有记录的旅客
        Collection<Traveler> travelers = idTravelerMap.values();
        if (CollectionUtil.isNotEmpty(travelers)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUser user;
            if (authentication != null) {
                user = (JwtUser) authentication.getPrincipal();
            } else {
                user = JwtUser.builder().id(0).realName("系统").build();
            }
            List<TemperatureRecord> recordList = travelers.stream()
                    .map(TemperatureRecord::new)
                    .peek(e -> e.setCreateUser(user))
                    .collect(Collectors.toList());
            temperatureRecordMapper.insertBatch(recordList);
            travelers.forEach(t -> log.info("生成旅客体温记录,id:{}", t.getId()));
        }
    }

    /**
     * 查询旅客体温记录
     *
     * @param travelerId
     * @return
     */
    @Override
    public List<TemperatureRecordVO> getTemperatureRecordByTraveler(Integer travelerId) {
        QueryWrapper<TemperatureRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("traveler_id ", travelerId);
        List<TemperatureRecord> recordList = temperatureRecordMapper.selectList(queryWrapper);
        return recordList.stream().map(TemperatureRecordVO::assemble).collect(Collectors.toList());
    }

}
