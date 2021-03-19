package site.minnan.entry.domain.vo.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.entity.TrainData;

@ApiModel("车次列表显示数据")
@Data
@Builder
public class TrainVO {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "车牌号码")
    private String carNumber;

    @ApiModelProperty(value = "司机姓名", example = "张三丰")
    private String driverName;

    @ApiModelProperty(value = "司机电话号码", example = "13813812138")
    private String driverPhone;

    @ApiModelProperty(value = "跟车人姓名", example = "张无忌")
    private String followerName;

    @ApiModelProperty(value = "跟车人电话号码", example = "17373737733")
    private String followerPhone;

    @ApiModelProperty(value = "目的地酒店", example = "荔湾酒店")
    private String hotel;

    @ApiModelProperty(value = "出发点口岸", example = "横琴口岸")
    private String port;

    @ApiModelProperty(value = "旅客数量", example = "23")
    private Integer travelerCount;

    @ApiModelProperty(value = "状态", example = "已出发")
    private String status;

    @ApiModelProperty(value = "状态码", example = "DEPARTED", allowableValues = "WAITING-未出发,DEPARTED-已出发,ARRIVED-已抵达")
    private String statusCode;

    public static TrainVO assemble(TrainData train) {
        return builder()
                .id(train.getId())
                .carNumber(train.getCarNumber())
                .driverName(train.getDriverName())
                .driverPhone(train.getDriverPhone())
                .followerName(train.getFollowerName())
                .followerPhone(train.getFollowerPhone())
                .port(train.getPortName())
                .hotel(train.getHotelName())
                .status(train.getStatus().getStatus())
                .statusCode(train.getStatus().getValue())
                .travelerCount(train.getTravelerCount())
                .build();
    }
}
