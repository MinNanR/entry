package site.minnan.entry.infrastructure.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 车次状态
 * @author Minnan on 2021/3/17
 */
public enum TrainStatus {

    WAITING("WAITING","未出发"),
    DEPARTED("DEPARTED","已出发"),
    ARRIVED("ARRIVED","已抵达");

    @EnumValue
    private final String value;

    @JsonValue
    private final String status;

    TrainStatus(String value, String status) {
        this.value = value;
        this.status = status;
    }

    public String getValue(){
        return value;
    }

    public String getStatus(){
        return status;
    }
}
