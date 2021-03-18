package site.minnan.entry.userinterface.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 添加用户参数
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("添加用户参数")
@Data
public class AddUserDTO {

    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "真实姓名", required = true, example = "张三")
    @NotEmpty(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "角色，忽略大小写",required = true, allowableValues = "ADMIN,PORT_USER,HOTEL_USER", example =
            "ADMIN")
    @NotEmpty(message = "未指定创建用户的角色")
    private String role;
}
