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

/**
 *
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
}
