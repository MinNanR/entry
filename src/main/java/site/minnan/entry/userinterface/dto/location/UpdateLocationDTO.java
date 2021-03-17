package site.minnan.entry.userinterface.dto.location;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 更新位置参数
 *
 * @author Minnan on 2021/3/16
 */
@Data
public class UpdateLocationDTO {

    @ApiModelProperty(value = "位置id", required = true, example = "1")
    @NotNull(message = "未指定更新的位置")
    private Integer id;

    @ApiModelProperty(value = "位置名称",  example = "荔湾酒店")
    @NotEmpty(message = "名称不能为空")
    private String locationName;

    @ApiModelProperty(value = "省份",example = "广东")
    private String province;

    @ApiModelProperty(value = "城市", example = "珠海")
    private String city;

    @ApiModelProperty(value = "地址",example = "香洲区101号")
    private String address;
}
