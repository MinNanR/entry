package site.minnan.entry.userinterface.dto.temperture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel("获取体温记录参数")
@Data
public class GetTemperatureRecordDTO extends ListQueryDTO {

    @ApiModelProperty(value = "日期（格式：yyyy-MM-dd）", required = true, example = "2021-03-17")
    @NotEmpty(message = "查询日期不能为空")
    private String date;

    @ApiModelProperty(value = "酒店id", example = "1")
    private Integer hotelId;

    @ApiModelProperty(value = "旅客名称", example = "张")
    private String travelerName;
}
