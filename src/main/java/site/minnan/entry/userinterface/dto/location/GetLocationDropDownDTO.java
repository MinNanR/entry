package site.minnan.entry.userinterface.dto.location;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("获取位置下拉框参数")
@Data
public class GetLocationDropDownDTO {

    @ApiModelProperty(value = "省份", example = "广东")
    private String province;

    @ApiModelProperty(value = "城市", example = "城市")
    private String city;

    @ApiModelProperty(value = "位置类型", example = "HOTEL", allowableValues = "HOTEL-酒店,PORT-口岸")
    private String type;
}
