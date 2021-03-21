package site.minnan.entry.userinterface.dto.temperture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("记录旅客体温参数")
@Data
public class RecordTemperatureDTO {

    @ApiModelProperty(value = "体温检测记录id（注意不是旅客id）", required = true, example = "1")
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "体温是否正常", example = "1", allowableValues = "1-正常,0-异常")
    private Integer isNormal;

    @ApiModelProperty(value = "体温", example = "37.1")
    private String temperature;
}
