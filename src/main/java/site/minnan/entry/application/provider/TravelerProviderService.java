package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.aggregate.Traveler;

import java.util.Collection;
import java.util.List;

public interface TravelerProviderService {

    /**
     * 旅客登车
     *
     * @param travelerId 旅客id
     * @param train      车次
     */
    void travelerBoard(Collection<Integer> travelerId, Train train);

    /**
     * 查询指定车次的人数
     *
     * @param trainId 车次id
     */
    int countTravelerOnTrain(Integer trainId);

    /**
     * 车次出发时修改乘客状态
     *
     * @param trainId
     */
    void travelerDepart(Integer trainId);

    /**
     * 车辆到达
     *
     * @param trains
     */
    void travelerArrive(Collection<Train> trains);

    /**
     * 批量获取旅客信息
     *
     * @param ids
     * @return
     */
    List<Traveler> getTravelerListByIds(Collection<Integer> ids);
}
