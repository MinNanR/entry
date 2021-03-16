package site.minnan.entry.userinterface.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 列表查询参数基类
 *
 * @author Minnan on 2020/12/16
 */
@ApiModel("列表查询参数")
@Data
public class ListQueryDTO {

    @ApiModelProperty(value = "页码", required = true, example = "1")
    @NotNull(message = "页码不能为空")
    private Integer pageIndex;

    @ApiModelProperty(value = "每页显示数量", required = true, example = "10")
    @NotNull(message = "显示数量不能为空")
    private Integer pageSize;
}
