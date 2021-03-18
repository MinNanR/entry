package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;
import site.minnan.entry.infrastructure.enumerate.Role;

@TableName("auth_user")
@Getter
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
    @Setter
    private String realName;

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

    /**
     * 修改密码
     *
     * @param encodedPassword 加密后的密码
     * @param passwordStamp   新密码戳
     */
    public void updatePassword(String encodedPassword, String passwordStamp) {
        this.password = encodedPassword;
        this.passwordStamp = passwordStamp;
    }

    public void disable() {
        this.enabled = DISABLE;
    }

    public void enable() {
        this.enabled = ENABLE;
    }

    public AuthUser(Integer id){
        this.id = id;
    }
}