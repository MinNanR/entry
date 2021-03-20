package site.minnan.entry.userinterface.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * @author Minnan
 */
@ApiModel("获取即将抵达的车次列表")
@Data
public class GetNotArrivedTrainListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "酒店id",example = "2")
    private Integer hotelId;

    @ApiModelProperty(value = "车牌号码",example = "粤S")
    private String carNumber;
}
