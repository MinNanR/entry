package site.minnan.entry.userinterface.dto.user;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.userinterface.dto.ListQueryDTO;

@ApiModel("查询用户列表参数")
@Data
public class GetUserListDTO extends ListQueryDTO {

    @ApiModelProperty(value = "用户名", allowEmptyValue = true)
    private String username;

    @ApiModelProperty(value = "用户角色", allowableValues = "ADMIN,PORT_USER,HOTEL_USER", example = "ADMIN")
    private String role;
}
