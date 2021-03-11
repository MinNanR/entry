package site.minnan.entry.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.aggregate.AuthUser;

@ApiModel("用户列表数据")
@Builder
@Data
public class UserVO {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("正式姓名")
    private String realName;

    public static UserVO assemble(AuthUser authUser) {
        return UserVO.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .realName(authUser.getUsername())
                .build();
    }
}
