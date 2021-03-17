package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * @author Minnan on 2021/03/16
 */
@ApiModel("查询旅客列表参数")
@Data
public class GetTravelerListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "范围查询开始时间（时间格式：yyyy-MM-dd HH:mm）", example = "2021-03-01 00:00")
    private String startTime;

    @ApiModelProperty(value = "范围查询结束时间（时间格式：yyyy-MM-dd HH:mm）", example = "2021-03-30 18:00")
    private String endTime;

    @ApiModelProperty(value = "旅客姓名", example = "张")
    private String name;
}
