package site.minnan.entry.application.provider;

import java.util.Collection;
import java.util.Map;

public interface TravelerProviderService {

    /**
     * 旅客登车
     *
     * @param travelerId 旅客id
     * @param trainId    车次id
     */
    void travelerBoard(Collection<Integer> travelerId, Integer trainId);
}
