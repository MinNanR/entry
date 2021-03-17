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
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;

import java.util.Optional;

/**
 * 地点实体（口岸/酒店）
 *
 * @author Minnan on 2021/03/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@TableName("entry_location")
public class Location extends ModifiableEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地点类型（口岸/酒店）
     */
    private LocationType type;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

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

    public void update(UpdateLocationDTO dto) {
        Optional.ofNullable(dto.getLocationName()).ifPresent(s -> this.name = s);
        Optional.ofNullable(dto.getProvince()).ifPresent(s -> this.province = s);
        Optional.ofNullable(dto.getCity()).ifPresent(s -> this.city = s);
        Optional.ofNullable(dto.getAddress()).ifPresent(s -> this.address = s);
    }
}
