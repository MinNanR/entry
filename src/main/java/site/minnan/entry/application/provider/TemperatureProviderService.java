package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;

import java.util.Collection;
import java.util.List;

/**
 * @author Minnan
 */
public interface TemperatureProviderService {

    /**
     * 创建体温测量记录
     */
    void createTemperatureRecord();

    /**
     * 查询旅客体温记录
     *
     * @param travelerId
     * @return
     */
    List<TemperatureRecordVO> getTemperatureRecordByTraveler(Integer travelerId);
}
