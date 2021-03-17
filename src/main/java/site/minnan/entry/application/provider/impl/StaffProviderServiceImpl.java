package site.minnan.entry.application.provider.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.entity.JwtUser;

/**
 *
 * @author Minnan on 2021/3/17
 */
@Service
public class StaffProviderServiceImpl implements StaffProviderService {

    /**
     * 根据用户获取工作人员信息
     *
     * @param user
     * @return
     */
    @Override
    @Cacheable("staff")
    public Staff getStaffByUser(JwtUser user) {

        return null;
    }
}
