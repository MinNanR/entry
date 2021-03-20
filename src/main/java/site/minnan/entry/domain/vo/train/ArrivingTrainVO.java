package site.minnan.entry.domain.vo.train;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.entity.TrainData;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel("即将抵达的车次展示数据")
@Builder
@Data
public class ArrivingTrainVO {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "司机姓名", example = "张三丰")
    private String driverName;

    @ApiModelProperty(value = "司机电话号码", example = "13813812138")
    private String driverPhone;

    @ApiModelProperty(value = "跟车人姓名", example = "张无忌")
    private String followerName;

    @ApiModelProperty(value = "跟车人电话号码", example = "17373737733")
    private String followerPhone;

    private String carNumber;

    @ApiModelProperty(value = "目的地酒店", example = "荔湾酒店")
    private String hotelName;

    @ApiModelProperty(value = "出发点口岸", example = "横琴口岸")
    private String portName;

    @ApiModelProperty(value = "出发时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-17 12:00")
    private String departureTime;

    @ApiModelProperty(value = "旅客数量", example = "40")
    private Integer travelerCount;

    public static ArrivingTrainVO assemble(TrainData train) {
        return builder()
                .id(train.getId())
                .carNumber(train.getCarNumber())
                .driverName(train.getDriverName())
                .driverPhone(train.getDriverPhone())
                .followerName(train.getFollowerName())
                .followerPhone(train.getFollowerPhone())
                .portName(train.getPortName())
                .hotelName(train.getHotelName())
                .departureTime(DateUtil.format(train.getDepartureTime(), "yyyy-MM-dd HH:mm"))
                .travelerCount(train.getTravelerCount())
                .build();
    }
}
