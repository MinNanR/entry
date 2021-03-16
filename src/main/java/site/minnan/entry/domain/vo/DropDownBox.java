package site.minnan.entry.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用下拉框数据
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("同下拉框数据")
@Data
@AllArgsConstructor
public class DropDownBox {

    @ApiModelProperty("key值")
    private String key;

    @ApiModelProperty("value值")
    private String value;
}
