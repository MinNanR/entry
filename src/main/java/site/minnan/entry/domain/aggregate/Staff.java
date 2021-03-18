package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;
import site.minnan.entry.infrastructure.enumerate.LocationType;

/**
 * 工作人员（口岸/酒店）
 *
 * @author Minnan on 2021/3/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@TableName("entry_staff")
public class Staff extends ModifiableEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号id
     */
    private Integer userId;

    /**
     * 工作人员姓名
     */
    private String staffName;

    /**
     * 工作地点类型
     */
    private LocationType locationType;


    /**
     * 设置创建者
     *
     * @param jwtUser 创建者
     */
    @Override
    public void setCreateUser(JwtUser jwtUser) {
        super.setCreateUser(jwtUser.getId(), jwtUser.getRealName());
    }

    /**
     * 设置更新者
     *
     * @param jwtUser 更新者
     */
    @Override
    public void setUpdateUser(JwtUser jwtUser) {
        super.setUpdateUser(jwtUser.getId(), jwtUser.getRealName());
    }

    public Staff(AuthUser user) {
        this.userId = user.getId();
        this.staffName = user.getRealName();
        this.locationType = user.getRole().getLocationType();
    }
}
