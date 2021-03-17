package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;

import java.sql.Timestamp;

/**
 * 车次记录
 *
 * @author Minnan on 2021/03/15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@TableName("entry_train_record")
public class Train extends ModifiableEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 司机电话号码
     */
    private String driverPhone;

    /**
     * 跟车人姓名
     */
    private String followerName;

    /**
     * 跟车人电话
     */
    private String followerPhone;

    /**
     * 送往的酒店id
     */
    private Integer hotelId;

    /**
     * 送往的酒店名称
     */
    private String hotelName;

    /**
     * 出发时间
     */
    private Timestamp departureTime;

    /**
     * 抵达时间
     */
    private Timestamp arriveTime;

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
}
