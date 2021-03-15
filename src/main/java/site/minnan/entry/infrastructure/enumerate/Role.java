package site.minnan.entry.infrastructure.enumerate;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户类型枚举
 * created by Minnan on 2020/12/17
 */
public enum Role {

    ADMIN("ADMIN", "管理员"),
    PORT_USER("PORT_USER", "口岸用户"),
    HOTEL_USER("HOTEL_USER","酒店用户");

    @EnumValue
    private final String value;

    @JsonValue
    private final String roleName;

    Role(String value, String roleName) {
        this.value = value;
        this.roleName = roleName;
    }

    public String getValue(){
        return value;
    }

    public String getRoleName(){
        return roleName;
    }
}
