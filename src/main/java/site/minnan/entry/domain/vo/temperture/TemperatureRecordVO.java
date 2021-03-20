package site.minnan.entry.domain.vo.temperture;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.aggregate.TemperatureRecord;
import site.minnan.entry.infrastructure.enumerate.TemperatureRecordStatus;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel("体温测量记录展示数据")
@Builder
@Data
public class TemperatureRecordVO {

    @ApiModelProperty(value = "检测记录id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "旅客id", example = "1")
    private Integer travelerId;

    @ApiModelProperty(value = "旅客姓名", example = "张大炮")
    private String travelerName;

    @ApiModelProperty(value = "酒店名称", example = "荔湾酒店")
    private String hotelName;

    @ApiModelProperty(value = "温度度数", example = "37.0")
    private String temperature;

    @ApiModelProperty(value = "是否正常", example = "1", allowableValues = "1-正常，0-异常")
    private Integer isNormal;

    @ApiModelProperty(value = "状态", example = "未测量")
    private String status;

    @ApiModelProperty(value = "状态码", example = "UNRECORDED", allowableValues = "UNRECORDED-未测量,RECORDED-已测量")
    private String statusCode;

    @ApiModelProperty(value = "检测时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-17 09:00")
    private String measureTime;

    public static TemperatureRecordVO assemble(TemperatureRecord record) {
        TemperatureRecordVOBuilder builder = builder()
                .id(record.getId())
                .travelerId(record.getTravelerId())
                .travelerName(record.getTravelerName())
                .hotelName(record.getHotelName())
                .status(record.getStatus().getStatus())
                .statusCode(record.getStatus().getValue());
        if (TemperatureRecordStatus.RECORDED.equals(record.getStatus())) {
            String measureTime = DateUtil.format(record.getMeasureTime(), "yyyy-MM-dd HH:mm");
            builder.measureTime(measureTime)
                    .temperature(record.getTemperature())
                    .isNormal(record.getTemperatureNormal());
        }
        return builder.build();

    }
}
