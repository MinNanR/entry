package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.entity.JwtUser;

/**
 * @author Minnan on 2021/3/17
 */
public interface StaffProviderService {

    /**
     * 根据用户获取工作人员信息
     *
     * @param userId 用户id
     * @return
     */
    Staff getStaffByUser(Integer userId);

    /**
     * 添加工作人员
     *
     * @param user       用户
     * @param locationId 工作地点id
     */
    void addStaff(AuthUser user, Integer locationId);
}
