package site.minnan.entry.userinterface.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改指定用户密码参数
 * @author Minnan on 2021/3/16
 */
@ApiModel("修改指定用户密码参数")
@Data
public class UpdatePasswordDTO {

    @ApiModelProperty(value = "目标用户id", required = true, example = "1")
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "新密码（MD5加密）", required = true, example = "e10adc3949ba59abbe56e057f20f883e")
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
