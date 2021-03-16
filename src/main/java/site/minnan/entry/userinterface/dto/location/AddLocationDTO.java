package site.minnan.entry.userinterface.dto.location;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 添加位置参数
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("添加位置参数")
@Data
public class AddLocationDTO {

    @ApiModelProperty(value = "位置名称", required = true, example = "横琴口岸")
    @NotEmpty(message = "位置名称不能为空")
    private String locationName;

    @ApiModelProperty(value = "位置类型", required = true, allowableValues = "PORT,HOTEL", example = "PORT")
    @NotEmpty(message = "未指定位置类型")
    private String locationType;

    @ApiModelProperty(value = "省份", required = true, example = "广东")
    @NotEmpty(message = "省份不能为空")
    private String province;

    @ApiModelProperty(value = "城市", required = true, example = "珠海")
    @NotEmpty(message = "城市不能为空")
    private String city;

    @ApiModelProperty(value = "地址", example = "香洲区环岛东路")
    private String address;
}
