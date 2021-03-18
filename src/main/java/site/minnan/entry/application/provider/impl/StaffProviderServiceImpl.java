package site.minnan.entry.application.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.LocationProviderService;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.aggregate.Location;
import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.StaffMapper;
import site.minnan.entry.infrastructure.enumerate.LocationType;

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
     */
    @Transactional
    public void addStaff(AuthUser user) {
        Staff staff = new Staff(user);
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        staff.setCreateUser(jwtUser);
        staffMapper.insert(staff);
    }
}
