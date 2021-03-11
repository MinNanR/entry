package site.minnan.entry.userinterface.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

@ApiModel("查询用户列表参数")
@Data
public class GetUserListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "用户名", allowEmptyValue = true)
    private String username;
}
