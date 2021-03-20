package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

import javax.validation.constraints.NotNull;

/**
 * @author Minnan
 */
@ApiModel("根据车次获取乘客参数")
@Data
public class GetTravelerListByTrainDTO extends ListQueryDTO {

    @ApiModelProperty(value = "车次id", required = true, example = "1")
    @NotNull(message = "未指定查询的车次")
    private Integer trainId;
}
