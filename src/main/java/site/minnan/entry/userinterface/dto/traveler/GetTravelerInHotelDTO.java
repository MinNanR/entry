package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * @author Minnan
 */
@ApiModel("查询在店旅客参数")
@Data
public class GetTravelerInHotelDTO extends ListQueryDTO {

    @ApiModelProperty(value = "旅客姓名", example = "张")
    private String name;

    @ApiModelProperty(value = "酒店id", example = "1")
    private Integer hotelId;
}
