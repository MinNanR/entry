package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;
import site.minnan.entry.infrastructure.enumerate.Role;

@TableName("auth_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser extends ModifiableEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private Role role;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    private Integer enabled;

    /**
     * 密码戳（比较密码版本）
     */
    private String passwordStamp;

    /**
     * 启用状态
     */
    public final static Integer ENABLE = 1;

    /**
     * 禁用状态
     */
    public final static Integer DISABLE = 0;

    public void setCreateUser(JwtUser user) {
        super.setCreateUser(user.getId(), user.getRealName());
    }

    public void setUpdateUser(JwtUser user) {
        setUpdateUser(user.getId(), user.getRealName());
    }
}