package site.minnan.entry.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 地点枚举
 *
 * @author Minnan on 2021/3/16
 */
public enum LocationType {
    PORT("PORT", "口岸"),
    HOTEL("HOTEL", "酒店");

    @EnumValue
    private final String value;

    @JsonValue
    private final String type;

    LocationType(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
