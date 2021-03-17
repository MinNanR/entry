package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel("酒店数据面板")
@Data
public class HotelData {

    @ApiModelProperty(value = "即将到店人数", example = "40")
    private Integer arriving;

    @ApiModelProperty(value = "当前在店人数", example = "100")
    private Integer peopleInHotel;
}
