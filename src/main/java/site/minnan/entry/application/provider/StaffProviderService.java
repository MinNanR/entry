package site.minnan.entry.application.provider;

import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.aggregate.Staff;
import site.minnan.entry.domain.entity.JwtUser;

import java.util.Collection;
import java.util.Map;

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

    /**
     * 获取工作人员的工作地点
     *
     * @param userIds 用户id
     * @return key：用户id，用户工作地点
     */
    Map<Integer, String> getStaffLocationMap(Collection<Integer> userIds);
}
