package site.minnan.entry.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel("用户列表数据")
@Builder
@Data
public class UserVO {

    @ApiModelProperty(value = "用户名", example = "13813812138")
    private String username;

    @ApiModelProperty(value = "真实姓名", example = "张大炮")
    private String realName;

    @ApiModelProperty(value = "角色", example = "口岸人员")
    private String role;

    @ApiModelProperty(value = "位置", example = "香洲区环岛东路")
    private String locationName;

    @ApiModelProperty(value = "创建时间（格式：yyyy-MM-dd HH:mm", example = "2021-03-16 09:00")
    private String createTime;

}
