package site.minnan.entry.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.service.LocationService;
import site.minnan.entry.domain.aggregate.Location;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.LocationMapper;
import site.minnan.entry.infrastructure.enumerate.LocationType;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

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
}
