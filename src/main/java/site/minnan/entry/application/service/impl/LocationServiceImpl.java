package site.minnan.entry.application.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.service.LocationService;
import site.minnan.entry.domain.aggregate.Location;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.LocationMapper;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.location.LocationVO;
import site.minnan.entry.infrastructure.enumerate.LocationType;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.infrastructure.exception.EntityNotExistException;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationDropDownDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationListDTO;
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    @Qualifier("BlankFilter")
    private Function<String, Optional<String>> blankFilter;

    /**
     * 添加位置
     *
     * @param dto
     */
    @Override
    public void addLocation(AddLocationDTO dto) {
        Location location = Location.builder()
                .name(dto.getLocationName())
                .type(LocationType.valueOf(dto.getLocationType()))
                .province(dto.getProvince())
                .city(dto.getCity())
                .address(dto.getAddress())
                .build();
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        location.setCreateUser(jwtUser);
        locationMapper.insert(location);
    }

    /**
     * 获取位置列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<LocationVO> getLocationList(GetLocationListDTO dto) {
        QueryWrapper<Location> queryWrapper = new QueryWrapper<>();
        blankFilter.apply(dto.getLocationName()).ifPresent(s -> queryWrapper.eq("name", s));
        blankFilter.apply(dto.getLocationType()).ifPresent(s -> queryWrapper.eq("type", s));
        queryWrapper.orderByDesc("update_time");
        Page<Location> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Location> page = locationMapper.selectPage(queryPage, queryWrapper);
        List<LocationVO> list = page.getRecords().stream().map(LocationVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 更新位置信息
     *
     * @param dto
     */
    @Override
    public void updateLocation(UpdateLocationDTO dto) {
        Location location = locationMapper.selectById(dto.getId());
        if (location == null) {
            throw new EntityNotExistException("位置不存在");
        }
        location.update(dto);
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        location.setUpdateUser(jwtUser);
        locationMapper.updateById(location);
    }

    /**
     * 获取位置下拉框
     *
     * @param dto
     * @return
     */
    @Override
    public List<DropDownBox> getDropDownBox(GetLocationDropDownDTO dto) {
        QueryWrapper<Location> queryWrapper = new QueryWrapper<>();
        blankFilter.apply(dto.getType()).ifPresent(s -> queryWrapper.eq("type", s));
        blankFilter.apply(dto.getProvince()).ifPresent(s -> queryWrapper.eq("province", s));
        blankFilter.apply(dto.getCity()).ifPresent(s -> queryWrapper.eq("city", s));
        List<Location> list = locationMapper.selectList(queryWrapper);
        return list.stream().map(e -> new DropDownBox(e.getId().toString(), e.getName())).collect(Collectors.toList());
    }
}
