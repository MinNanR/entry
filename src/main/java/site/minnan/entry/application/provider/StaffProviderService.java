package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.entity.JwtUser;

/**
 * @author Minnan on 2021/3/17
 */
public interface StaffProviderService {

    /**
     * 根据用户获取工作人员信息
     *
     * @param user
     * @return
     */
    Staff getStaffByUser(JwtUser user);
}
