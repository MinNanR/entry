package site.minnan.entry.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.service.TravelerService;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.TravelerMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
import site.minnan.entry.infrastructure.enumerate.Gender;
import site.minnan.entry.infrastructure.enumerate.TrainStatus;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;
import site.minnan.entry.infrastructure.exception.EntityNotExistException;
import site.minnan.entry.infrastructure.exception.UnmodifiableException;
import site.minnan.entry.userinterface.dto.traveler.AddTravelerDTO;
import site.minnan.entry.userinterface.dto.traveler.GetTravelerListDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelerServiceImpl implements TravelerService {

    @Autowired
    private TravelerMapper travelerMapper;

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
}