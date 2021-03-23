package site.minnan.entry.userinterface.dto.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

/**
 * 日志查询参数
 *
 * @author Minnan on 2021/3/3
 */
@ApiModel("日志查询参数")
@Data
public class GetLogListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "用户名", example = "张三")
    private String username;

    @ApiModelProperty(value = "用户操作", example = "登录", allowableValues = "LOGIN,ADD,UPDATE,DELETE,DOWNLOAD,LOGOUT")
    private String operation;
}
