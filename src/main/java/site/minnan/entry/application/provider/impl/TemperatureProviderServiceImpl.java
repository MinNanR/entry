package site.minnan.entry.application.provider.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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
        List<TemperatureRecord> todayRecord = temperatureRecordMapper.getTodayRecord();//获取已有记录的旅客
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
        }
    }
}
