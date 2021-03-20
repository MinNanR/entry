package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.Traveler;

import java.util.Collection;

/**
 * @author Minnan
 */
public interface TemperatureProviderService {

    /**
     * 创建体温测量记录
     */
    void createTemperatureRecord();
}
