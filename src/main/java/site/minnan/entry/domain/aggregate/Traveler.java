package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;
import site.minnan.entry.infrastructure.enumerate.Gender;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 旅客实体
 *
 * @author Minnan on 2021/3/15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("entry_traveler")
public class Traveler extends ModifiableEntity {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 旅客姓名
     */
    private String name;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 省份
     */
    private String province;

    /**
     * 旅客证件号码
     */
    private String cardNumber;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 入境口岸id
     */
    private Integer portId;

    /**
     * 入境口岸名称
     */
    private String portName;

    /**
     * 入境时间
     */
    private Timestamp entryTime;

    /**
     * 登车时间
     */
    private Timestamp boardingTime;

    /**
     * 车次id
     */
    private Integer trainId;

    /**
     * 送往酒店时间（入住时间）
     */
    private Timestamp arrivalTime;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 隔离开始时间
     */
    private Timestamp quarantineStartTime;

    /**
     * 隔离结束时间
     */
    private Timestamp quarantineEndTime;

    /**
     * 旅客状态
     */
    private TravelerStatus status;

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

    public void arrive(Train train) {
        this.hotelId = train.getHotelId();
        this.hotelName = train.getHotelName();
        this.arrivalTime = train.getArriveTime();
    }

    /**
     * 旅客解除隔离
     *
     * @param time 解除隔离时间
     */
    public void endQuarantine(Timestamp time) {
        this.status = TravelerStatus.RELEASED;
        this.quarantineEndTime = time;
    }
}
