package site.minnan.entry.domain.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 可修改的实体类，用于记录创建时间与修改时间
 *
 * @author Minnan on 2021/3/15
 */
@NoArgsConstructor
@Getter
public abstract class ModifiableEntity {

    @ApiModelProperty("创建者id")
    private Integer createUserId;

    @ApiModelProperty("创建者姓名")
    private String createUsername;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("更新人id")
    private Integer updateUserId;

    @ApiModelProperty("更新人姓名")
    private String updateUsername;

    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    /**
     * 设置创建者
     *
     * @param jwtUser 创建者
     */
    public abstract void setCreateUser(JwtUser jwtUser);

    /**
     * 设置更新者
     *
     * @param jwtUser 更新者
     */
    public abstract void setUpdateUser(JwtUser jwtUser);

    public void setCreateUser(Integer userId, String username, Timestamp createTime) {
        this.createUserId = this.updateUserId = userId;
        this.createUsername = this.updateUsername = username;
        this.createTime = this.updateTime = createTime;
    }

    public void setUpdateUser(Integer userId, String username, Timestamp createTime) {
        this.updateUserId = userId;
        this.updateUsername = username;
        this.updateTime = createTime;
    }

    protected void setCreateUser(Integer userId, String username) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        setCreateUser(userId, username, time);
    }

    protected void setUpdateUser(Integer userId, String username) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        setUpdateUser(userId, username, time);
    }
}
