package site.minnan.entry.domain.vo.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author Minnan on 2021/3/17
 */
@ApiModel("已抵达的车次展示数据")
@Data
public class ArrivedTrainVO {

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

    @ApiModelProperty(value = "目的地酒店", example = "荔湾酒店")
    private Integer destinationHotel;

    @ApiModelProperty(value = "出发点口岸", example = "横琴口岸")
    private Integer departPort;

    @ApiModelProperty(value = "出发时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-17 12:00")
    private String departTime;

    @ApiModelProperty(value = "抵达时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-17 14:00")
    private String arriveTime;

    @ApiModelProperty(value = "旅客数量", example = "40")
    private Integer travelerCount;
}
