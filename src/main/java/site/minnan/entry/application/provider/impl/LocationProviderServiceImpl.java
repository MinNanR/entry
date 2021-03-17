package site.minnan.entry.application.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.LocationProviderService;
import site.minnan.entry.domain.aggregate.Location;
import site.minnan.entry.domain.mapper.LocationMapper;

@Service
public class LocationProviderServiceImpl implements LocationProviderService {

    @Autowired
    private LocationMapper locationMapper;

    /**
     * 根据id获取位置
     *
     * @param locationId 位置id
     */
    @Override
    public Location getLocationById(Integer locationId) {
        return locationMapper.selectById(locationId);
    }
}
