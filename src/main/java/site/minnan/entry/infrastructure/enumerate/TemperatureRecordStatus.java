package site.minnan.entry.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 体温测量记录状态
 *
 * @author Minnan on 2021/3/17
 */
public enum TemperatureRecordStatus {

    UNRECORDED("UNRECORDED", "未测量"),
    RECORDED("RECORDED", "已测量");

    @EnumValue
    private final String value;

    @JsonValue
    private final String status;

    TemperatureRecordStatus(String value, String status) {
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
