package site.minnan.entry.userinterface.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class DetailsQueryDTO {

    @ApiModelProperty("id")
    private Integer id;
}
