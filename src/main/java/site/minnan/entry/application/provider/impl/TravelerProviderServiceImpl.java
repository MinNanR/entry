package site.minnan.entry.application.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.TravelerProviderService;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.mapper.TravelerMapper;

import java.util.Collection;
import java.util.Map;

@Service
public class TravelerProviderServiceImpl implements TravelerProviderService {

    @Autowired
    private TravelerMapper travelerMapper;

    /**
     * 旅客登车
     *
     * @param travelerId 旅客id
     * @param trainId    车次id
     */
    @Override
    public void travelerBoard(Collection<Integer> travelerId, Integer trainId) {

    }
}
