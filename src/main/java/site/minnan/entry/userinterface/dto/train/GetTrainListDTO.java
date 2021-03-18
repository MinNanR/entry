package site.minnan.entry.userinterface.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 *
 * @author Minnan on 2021/3/17
 */
@ApiModel("获取车次列表参数")
@Data
public class GetTrainListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "车牌号码",example = "4433Z")
    private String carNumber;

//    @ApiModelProperty(value = "开始时间（格式：yyyy-MM-dd）",example = "2021-03-16")
//    private String startTime;
//
//    @ApiModelProperty(value = "结束时间（格式：yyyy-MM-dd）",example = "2021-03-17")
//    private String endTime;
}
