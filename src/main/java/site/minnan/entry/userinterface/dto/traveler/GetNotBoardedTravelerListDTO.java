package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

import javax.validation.constraints.NotNull;

@Data
public class GetNotBoardedTravelerListDTO {

    @ApiModelProperty(value = "口岸id", required = true, example = "1")
    @NotNull(message = "未指定口岸")
    private Integer portId;

    @ApiModelProperty(value = "旅客姓名", required = true, example = "张")
    private String name;
}
