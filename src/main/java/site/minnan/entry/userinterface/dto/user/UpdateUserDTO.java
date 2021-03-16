package site.minnan.entry.userinterface.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户修改自己的信息参数
 * @author Minnan on 2021/3/16
 */
@ApiModel("用户修改自己的信息参数")
@Data
public class UpdateUserDTO {

    @ApiModelProperty(value = "新密码（MD5加密" ,example = "e10adc3949ba59abbe56e057f20f883e")
    private String newPassword;

    @ApiModelProperty(value = "真实姓名", example = "李四")
    private String realName;
}
