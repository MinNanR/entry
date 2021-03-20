package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.netty.util.internal.IntegerHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;
import site.minnan.entry.infrastructure.enumerate.TemperatureRecordStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("entry_temperature_record")
public class TemperatureRecord extends ModifiableEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 旅客id
     */
    private Integer travelerId;

    /**
     * 旅客姓名
     */
    private String travelerName;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 体温正常或异常
     */
    private Integer temperatureNormal;

    /**
     * 检测体温时间
     */
    private Timestamp measureTime;

    /**
     * 体温检测记录状态
     */
    private TemperatureRecordStatus status;

    /**
     * 体温正常
     */
    public static final Integer TEMPERATURE_NORMAL = 1;

    /**
     * 体温异常
     */
    public static final Integer TEMPERATURE_ABNORMAL = 0;

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

    public TemperatureRecord(Traveler traveler){
        this.travelerId = traveler.getId();
        this.travelerName = traveler.getName();
        this.hotelId = traveler.getHotelId();
        this.hotelName = traveler.getHotelName();
        this.status = TemperatureRecordStatus.UNRECORDED;
    }
}
