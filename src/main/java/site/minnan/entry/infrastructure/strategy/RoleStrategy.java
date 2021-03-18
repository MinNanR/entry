package site.minnan.entry.infrastructure.strategy;

import site.minnan.entry.infrastructure.enumerate.LocationType;

/**
 * 根据用户类型获取工作地点类型
 */
public interface RoleStrategy {

    /**
     * 返回工作地点类型（根据用户类型），管理员返回空
     * @return
     */
    LocationType getLocationType();
}
