package site.minnan.entry.userinterface.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 详情查询参数
 *
 * @author Minnan on 2020/12/21
 */
@ApiModel("对指定id对象操作参数")
@Data
public class DetailsQueryDTO {

    @ApiModelProperty("对象id")
    @NotNull(message = "未指定操作对象")
    private Integer id;
}