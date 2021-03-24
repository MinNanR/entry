package site.minnan.entry.application.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.TemperatureProviderService;
import site.minnan.entry.application.service.TravelerService;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.TravelerMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.*;
import site.minnan.entry.infrastructure.enumerate.Gender;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;
import site.minnan.entry.infrastructure.exception.EntityNotExistException;
import site.minnan.entry.infrastructure.exception.UnmodifiableException;
import site.minnan.entry.userinterface.dto.ListQueryDTO;
import site.minnan.entry.userinterface.dto.traveler.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("all")
public class TravelerServiceImpl implements TravelerService {

    @Autowired
    private TravelerMapper travelerMapper;

    @Autowired
    private TemperatureProviderService temperatureProviderService;

    /**
     * 添加旅客
     *
     * @param dto
     */
    @Override
    public void addTraveler(AddTravelerDTO dto) {
        Traveler traveler = Traveler.builder()
                .name(dto.getName())
                .cardNumber(dto.getCardNumber())
                .gender(Gender.valueOf(dto.getGender().toUpperCase()))
                .nationality(dto.getNationality())
                .province(dto.getProvince())
                .birthday(DateUtil.parse(dto.getBirthday()))
                .age(DateUtil.ageOfNow(dto.getBirthday()))
                .portId(dto.getPortId())
                .portName(dto.getPortName())
                .entryTime(new Timestamp(System.currentTimeMillis()))
                .status(TravelerStatus.ENTRY)
                .build();
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        traveler.setCreateUser(jwtUser);
        travelerMapper.insert(traveler);
    }

    /**
     * 人员明细
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TravelerVO> getTravelerList(GetTravelerListDTO dto) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dto.getStartTime()) && StrUtil.isNotBlank(dto.getEndTime())) {
            DateTime startTime = DateUtil.parse(dto.getStartTime(), "yyyy-MM-dd HH:mm");
            DateTime endTime = DateUtil.parse(dto.getEndTime(), "yyyy-MM-dd HH:mm");
            queryWrapper.between("entry_time", startTime, endTime);
        }
        Optional.ofNullable(dto.getName()).ifPresent(s -> queryWrapper.like("name", s));
        Page<Traveler> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Traveler> page = travelerMapper.selectPage(queryPage, queryWrapper);
        List<TravelerVO> list = page.getRecords().stream().map(TravelerVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 删除旅客
     *
     * @param travelerId 旅客id
     */
    @Override
    public void deleteTraveler(Integer travelerId) {
        Traveler traveler = travelerMapper.selectById(travelerId);
        if (traveler == null) {
            throw new EntityNotExistException("旅客不存在");
        } else if (!TravelerStatus.ENTRY.equals(traveler.getStatus())) {
            throw new UnmodifiableException("旅客不可删除");
        }
        travelerMapper.deleteById(travelerId);
    }

    /**
     * 获取旅客档案
     *
     * @param travelerId
     * @return
     */
    @Override
    public TravelerArchive getTravelerArchive(Integer travelerId) {
        Traveler traveler = travelerMapper.selectById(travelerId);
        if (traveler == null) {
            throw new EntityNotExistException("旅客不存在");
        }
        return new TravelerArchive(traveler);
    }

    /**
     * 获取未登车的旅客下拉框
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TravelerVO> getNotBoardedTraverList(GetNotBoardedTravelerListDTO dto) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name")
                .eq("port_id", dto.getPortId())
                .eq("status", TravelerStatus.ENTRY)
                .orderByDesc("update_time");
        Optional.ofNullable(dto.getName()).ifPresent(s -> queryWrapper.like("name", s));
        Page<Traveler> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Traveler> page = travelerMapper.selectPage(queryPage, queryWrapper);
        List<TravelerVO> list = page.getRecords().stream().map(TravelerVO::toDropDown).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 根据车次获取车上乘客
     *
     * @return
     */
    @Override
    public ListQueryVO<TravelerVO> getTravelerListByTrain(GetTravelerListByTrainDTO dto) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_id", dto.getTrainId())
                .orderByDesc("update_time");
        Page<Traveler> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Traveler> page = travelerMapper.selectPage(queryPage, queryWrapper);
        List<TravelerVO> list = page.getRecords().stream().map(TravelerVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 获取当前在店的旅客
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TravelerVO> getTravelerListInHotel(GetTravelerInHotelDTO dto) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getName()).ifPresent(s -> queryWrapper.like("name", s));
        Optional.ofNullable(dto.getHotelId()).ifPresent(s -> queryWrapper.eq("hotel_id", s));
        queryWrapper.in("status", TravelerStatus.NOT_QUARANTINE, TravelerStatus.QUARANTINE);
        Page<Traveler> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Traveler> page = travelerMapper.selectPage(queryPage, queryWrapper);
        List<TravelerVO> list = page.getRecords().stream().map(TravelerVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 获取未开始隔离的旅客列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TravelerVO> getNotQuarantineTravelerList(ListQueryDTO dto) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", TravelerStatus.NOT_QUARANTINE);
        Page<Traveler> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Traveler> page = travelerMapper.selectPage(queryPage, queryWrapper);
        List<TravelerVO> list = page.getRecords().stream().map(TravelerVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 开始隔离
     *
     * @param dto
     */
    @Override
    @Transactional
    public void startQuarantine(StartQuarantineDTO dto) {
        Collection<Integer> travelerIds = Assert.notEmpty(dto.getTravelerIds());
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UpdateWrapper<Traveler> updateWrapper = new UpdateWrapper<>();
        DateTime startDate = DateUtil.parseDate(dto.getStartTime());
        updateWrapper.set("status", TravelerStatus.QUARANTINE)
                .set("quarantine_start_time", startDate)
                .set("update_user_id", user.getId())
                .set("update_user_name", user.getRealName())
                .set("update_time", new Timestamp(System.currentTimeMillis()))
                .in("id", travelerIds);
        travelerMapper.update(null, updateWrapper);
        temperatureProviderService.createTemperatureRecord();
    }

    /**
     * 结束隔离
     *
     * @param travelerId 旅客id
     */
    @Override
    public void endQuarantine(Integer travelerId) {
        Traveler traveler = travelerMapper.selectById(travelerId);
        if (traveler == null) {
            throw new EntityNotExistException("旅客不存在");
        } else if (!TravelerStatus.QUARANTINE.equals(traveler.getStatus())) {
            throw new UnmodifiableException("旅客当前未被隔离");
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        long diff = DateUtil.between(current, traveler.getQuarantineStartTime(), DateUnit.DAY);
        if (diff < 14) {
            throw new UnmodifiableException("隔离时间未到14天");
        }
        traveler.endQuarantine(current);
        travelerMapper.updateById(traveler);
    }

    /**
     * 酒店面板数据
     *
     * @param dto
     * @return
     */
    @Override
    public HotelData getHotelData(GetHotelDataDTO dto) {
        return travelerMapper.getHotelData(dto);
    }

    /**
     * 获取人员波动数量分析
     *
     * @return
     */
    @Override
    public NumberTrendData getNumberTrend() {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        DateTime boundary = DateTime.now().offset(DateField.DAY_OF_MONTH, -7);
        boundary = DateUtil.beginOfDay(boundary);
        queryWrapper.gt("entry_time", boundary);
        List<Traveler> travelerList = travelerMapper.selectList(queryWrapper);
        Map<DateTime, List<Traveler>> groupByDate = travelerList.stream()
                .collect(Collectors.groupingBy(e -> DateUtil.beginOfDay(e.getEntryTime())));
        NumberTrendData vo = new NumberTrendData();
        groupByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> vo.add(entry.getValue().size(), entry.getKey()));
        return vo;
    }

    /**
     * 人员国籍归属分析
     *
     * @return
     */
    @Override
    public NationalityStatics getNationalityStatics() {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "nationality", "province");
        List<Traveler> travelerList = travelerMapper.selectList(queryWrapper);
        Map<String, List<Traveler>> groupByNationality = travelerList.stream()
                .collect(Collectors.groupingBy(Traveler::getNationality));
        AreaData nationalityData = new AreaData();
        groupByNationality.forEach((key, value) -> nationalityData.add(key, value.size()));
        List<Traveler> chineseList = groupByNationality.get("中国");
        AreaData provinceData = new AreaData();
        chineseList.stream()
                .collect(Collectors.groupingBy(Traveler::getProvince))
                .entrySet().stream()
                .sorted(Comparator.comparingInt(entry ->
                        ((Map.Entry<String, List<Traveler>>) entry).getValue().size()).reversed())
                .limit(7)
                .forEach(entry -> provinceData.add(entry.getKey(), entry.getValue().size()));
        return new NationalityStatics(nationalityData, provinceData);
    }

    /**
     * 获取已接受的旅客数量
     *
     * @return
     */
    @Override
    public int getAcceptedTravelerCount() {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", TravelerStatus.NOT_QUARANTINE,TravelerStatus.QUARANTINE, TravelerStatus.RELEASED);
        return travelerMapper.selectCount(queryWrapper);
    }
}