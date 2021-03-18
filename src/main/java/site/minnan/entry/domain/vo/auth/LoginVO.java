package site.minnan.entry.domain.vo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@ApiModel("登录数据")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginVO {

    @ApiModelProperty("登录token")
    private String authority;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("真实姓名")
    private String realName;

    public LoginVO(String authority, String role){
        this.authority = authority;
        this.role = role;
    }
}
