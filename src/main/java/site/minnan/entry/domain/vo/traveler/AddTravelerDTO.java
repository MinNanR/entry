package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 添加旅客参数
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("添加旅客参数")
@Data
public class AddTravelerDTO {

    @ApiModelProperty(value = "旅客姓名", required = true, example = "张三")
    @NotEmpty(message = "旅客姓名不能为空")
    private String name;

    @ApiModelProperty(value = "国籍", required = true, example = "中国")
    @NotEmpty(message = "国籍不能为空")
    private String nationality;
}
