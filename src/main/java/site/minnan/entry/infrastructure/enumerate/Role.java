package site.minnan.entry.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import site.minnan.entry.infrastructure.strategy.RoleStrategy;

/**
 * 用户类型枚举
 * created by Minnan on 2020/12/17
 */
public enum Role implements RoleStrategy {

    ADMIN("ADMIN", "管理员") {
        /**
         * 返回工作地点类型（根据用户类型），管理员返回空
         *
         * @return
         */
        @Override
        public LocationType getLocationType() {
            return null;
        }
    },
    PORT_USER("PORT_USER", "口岸用户") {
        /**
         * 返回工作地点类型（根据用户类型），管理员返回空
         *
         * @return
         */
        @Override
        public LocationType getLocationType() {
            return LocationType.PORT;
        }
    },
    HOTEL_USER("HOTEL_USER", "酒店用户") {
        /**
         * 返回工作地点类型（根据用户类型），管理员返回空
         *
         * @return
         */
        @Override
        public LocationType getLocationType() {
            return LocationType.HOTEL;
        }
    };

    @EnumValue
    private final String value;

    @JsonValue
    private final String roleName;

    Role(String value, String roleName) {
        this.value = value;
        this.roleName = roleName;
    }

    public String getValue() {
        return value;
    }

    public String getRoleName() {
        return roleName;
    }
}
