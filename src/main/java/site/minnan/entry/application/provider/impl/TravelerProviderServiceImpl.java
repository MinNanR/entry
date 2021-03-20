package site.minnan.entry.application.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.TravelerProviderService;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.TravelerMapper;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelerProviderServiceImpl implements TravelerProviderService {

    @Autowired
    private TravelerMapper travelerMapper;

    /**
     * 旅客登车
     *
     * @param travelerId 旅客id
     * @param train      车次
     */
    @Override
    @Transactional
    public void travelerBoard(Collection<Integer> travelerId, Train train) {
        UpdateWrapper<Traveler> updateWrapper = new UpdateWrapper<>();
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timestamp current = new Timestamp(System.currentTimeMillis());
        updateWrapper.set("train_id", train.getId())
                .set("status", TravelerStatus.BOARDED)
                .set("boarding_time", current)
                .set("update_user_id", user.getId())
                .set("update_user_name", user.getRealName())
                .set("update_time", current)
                .in("id", travelerId);
        travelerMapper.update(null, updateWrapper);
    }

    /**
     * 查询指定车次的人数
     *
     * @param trainId 车次id
     */
    @Override
    public int countTravelerOnTrain(Integer trainId) {
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_id", trainId);
        return travelerMapper.selectCount(queryWrapper);
    }

    /**
     * 车次出发时修改乘客状态
     *
     * @param trainId
     */
    @Override
    @Transactional
    public void travelerDepart(Integer trainId) {
        UpdateWrapper<Traveler> updateWrapper = new UpdateWrapper<>();
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        updateWrapper.eq("train_id", trainId)
                .set("status", TravelerStatus.TRANSPORTING)
                .set("update_user_id", user.getId())
                .set("update_user_name", user.getRealName())
                .set("update_time", timestamp);
        travelerMapper.update(null, updateWrapper);
    }

    /**
     * 车辆到达
     *
     * @param trains
     */
    @Override
    @Transactional
    public void travelerArrive(Collection<Train> trains) {
        Map<Integer, Train> trainIdMap = trains.stream().collect(Collectors.toMap(Train::getId, e -> e));
        QueryWrapper<Traveler> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("train_id", trainIdMap.keySet());
        List<Traveler> travelerList = travelerMapper.selectList(queryWrapper);
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Traveler traveler : travelerList) {
            Train train = trainIdMap.get(traveler.getTrainId());
            traveler.arrive(train);
            traveler.setUpdateUser(user);
        }
        travelerMapper.travelerArrive(travelerList);

    }
}
