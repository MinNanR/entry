package site.minnan.entry.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 旅客状态枚举
 *
 * @author Minnan on 2021/3/17
 */
public enum TravelerStatus {

    ENTRY("ENTRY", "已入境"),
    BOARDED("BOARDED", "已登车"),
    TRANSPORTING("TRANSPORTING", "运输中"),
    NOT_QUARANTINE("NOT_QUARANTINE", "未隔离"),
    QUARANTINE("QUARANTINE", "隔离中"),
    RELEASED("RELEASED", "已解除隔离");

    @EnumValue
    private final String value;

    @JsonValue
    private final String status;

    TravelerStatus(String value, String status) {
        this.value = value;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }
}
