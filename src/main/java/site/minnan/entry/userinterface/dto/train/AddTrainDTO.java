package site.minnan.entry.userinterface.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("创建车次参数")
@Data
public class AddTrainDTO {

    @ApiModelProperty(value = "车牌号", required = true, example = "粤S3888Q")
    @NotEmpty(message = "车牌号码不能为空")
    private String carNumber;

    @ApiModelProperty(value = "司机姓名", required = true, example = "张三丰")
    @NotEmpty(message = "司机姓名不能为空")
    private String driverName;

    @ApiModelProperty(value = "司机电话号码", required = true, example = "13813812138")
    @NotEmpty(message = "司机电话不能为空")
    private String driverPhone;

    @ApiModelProperty(value = "跟车人姓名", required = true, example = "张无忌")
    @NotEmpty(message = "跟车人姓名不能为空")
    private String followerName;

    @ApiModelProperty(value = "跟车人电话号码", required = true, example = "17373737733")
    @NotEmpty(message = "跟车人电话号码不能为空")
    private String followerPhone;

    @ApiModelProperty(value = "出发点口岸id", required = true, example = "5")
    @NotNull(message = "未填写出发口岸")
    private Integer portId;

    @ApiModelProperty(value = "出发点口岸名称", required = true, example = "横琴口岸")
    @NotEmpty(message = "未填写出发口岸")
    private String portName;

    @ApiModelProperty(value = "目的地酒店id", required = true, example = "2")
    @NotNull(message = "未填写地酒店")
    private Integer hotelId;

    @ApiModelProperty(value = "目的地酒店名称", required = true, example = "荔湾酒店")
    @NotEmpty(message = "未填写地酒店")
    private String hotelName;


}
