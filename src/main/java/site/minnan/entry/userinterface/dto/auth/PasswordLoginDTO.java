package site.minnan.entry.userinterface.dto.auth;

import io.swagger.annotations.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/***
 * 使用密码登录的参数
 * @author Minnan on 2021/3/11
 */
@ApiModel
@Data
public class PasswordLoginDTO {

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码（MD5加密）", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;
}
