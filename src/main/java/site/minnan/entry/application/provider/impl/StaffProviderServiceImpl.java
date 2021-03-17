package site.minnan.entry.application.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.LocationProviderService;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.aggregate.Location;
import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.mapper.StaffMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Minnan on 2021/3/17
 */
@Service
public class StaffProviderServiceImpl implements StaffProviderService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private LocationProviderService locationProviderService;

    /**
     * 根据用户获取工作人员信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    @Cacheable("staff")
    public Staff getStaffByUser(Integer userId) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return staffMapper.selectOne(queryWrapper);
    }

    /**
     * 添加工作人员
     *
     * @param user       用户
     * @param locationId 工作地点id
     */
    @Override
    public void addStaff(AuthUser user, Integer locationId) {
        Location location = locationProviderService.getLocationById(locationId);
        Staff staff = new Staff(user, location);
        staffMapper.insert(staff);
    }

    /**
     * 获取工作人员的工作地点
     *
     * @param userIds 用户id
     * @return key：用户id，用户工作地点
     */
    @Override
    public Map<Integer, String> getStaffLocationMap(Collection<Integer> userIds) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        List<Staff> staffList = staffMapper.selectList(queryWrapper);
        return staffList.stream().collect(Collectors.toMap(Staff::getUserId, Staff::getLocationName));
    }
}
