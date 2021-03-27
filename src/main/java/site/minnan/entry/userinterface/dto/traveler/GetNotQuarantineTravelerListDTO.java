package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("获取未隔离的旅客参数")
@Data
public class GetNotQuarantineTravelerListDTO {

    @ApiModelProperty(value = "酒店id", example = "3")
    private Integer hotelId;
}
