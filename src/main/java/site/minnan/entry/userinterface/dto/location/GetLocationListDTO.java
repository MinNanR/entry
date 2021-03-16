package site.minnan.entry.userinterface.dto.location;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * 查询位置列表参数
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("查询位置列表参数")
@Data
public class GetLocationListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "位置名称", example = "口岸")
    private String locationName;

    @ApiModelProperty(value = "位置类型", allowableValues = "PORT,HOTEL", example = "PORT")
    private String locationType;

}
