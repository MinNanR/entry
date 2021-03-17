package site.minnan.entry.userinterface.dto.temperture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel("获取体温记录参数")
@Data
public class GetTemperatureRecordDTO extends ListQueryDTO {

    @ApiModelProperty(value = "开始时间（格式：yyyy-MM-dd）", required = true, example = "2021-03-17")
    private String startTime;

    @ApiModelProperty(value = "结束时间（格式：yyyy-MM-dd）", required = true, example = "2021-03-17")
    private String endTime;

    @ApiModelProperty(value = "酒店id（管理员查看时携带）", required = true, example = "1")
    private Integer hotelId;
}
