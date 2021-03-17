package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.Location;

public interface LocationProviderService {

    /**
     * 根据id获取位置
     *
     * @param locationId 位置id
     */
    Location getLocationById(Integer locationId);
}
