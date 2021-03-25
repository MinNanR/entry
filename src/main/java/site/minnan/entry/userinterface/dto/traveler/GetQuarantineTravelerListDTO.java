package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

@ApiModel("获取正在隔离中的旅客(隔离时间超过14天)")
@Data
public class GetQuarantineTravelerListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "酒店id", example = "1")
    private Integer hotelId;

    @ApiModelProperty(value = "旅客姓名", example = "张三")
    private String name;
}
