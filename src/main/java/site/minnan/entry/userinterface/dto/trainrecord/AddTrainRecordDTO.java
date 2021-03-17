package site.minnan.entry.userinterface.dto.trainrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("创建车次参数")
@Data
public class AddTrainRecordDTO {

    @ApiModelProperty(value = "车牌号", required = true, example = "粤S3888Q")
    @NotEmpty(message = "车牌号码不能为空")
    private String carNumber;

    @ApiModelProperty(value = "司机姓名", required = true, example = "张三丰")
    @NotEmpty(message = "司机姓名不能为空")
    private String driverName;

    @ApiModelProperty(value = "司机电话号码", required = true, example = "13813812138")
    @NotEmpty(message = "司机电话不能为空")
    private String driverPhone;

    @ApiModelProperty(value = "目的地酒店id", required = true, example = "2")
    @NotNull
    private Integer destinationHotelId;

    @ApiModelProperty(value = "出发点口岸id（管理员添加时必传）")
    private Integer departPortId;
}
