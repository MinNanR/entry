package site.minnan.entry.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel("用户详细信息")
@Builder
@Data
public class UserInfoVO {

    @ApiModelProperty(value = "账号",example = "13813812138")
    private String username;

    @ApiModelProperty(value = "姓名",example = "张大炮")
    private String realName;

    @ApiModelProperty(value = "角色",example = "口岸人员")
    private String role;

    @ApiModelProperty(value = "所属位置（口岸/酒店）", example = "横琴口岸")
    private String locationName;

    @ApiModelProperty(value = "密码(空字符串）" ,example = "")
    private String password;
}
