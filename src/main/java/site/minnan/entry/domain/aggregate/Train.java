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
import site.minnan.entry.infrastructure.enumerate.TrainStatus;
import site.minnan.entry.userinterface.dto.train.AddTrainDTO;
import site.minnan.entry.userinterface.dto.traveler.AddTravelerDTO;

import java.sql.Timestamp;

/**
 * 车次记录
 *
 * @author Minnan on 2021/03/15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@TableName("entry_train")
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
     * 出发口岸id
     */
    private Integer portId;

    /**
     * 出发口岸名称
     */
    private String portName;

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
     * 车次状态
     */
    private TrainStatus status;

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

    /**
     * 车次状态转至出发
     *
     * @param departureTime 出发时间
     */
    public void depart(Timestamp departureTime) {
        this.status = TrainStatus.DEPARTED;
        this.departureTime = departureTime;
    }

    /**
     * 车辆达到酒店并转入
     *
     * @param arriveTime
     */
    public void arrive(Timestamp arriveTime) {
        this.status = TrainStatus.ARRIVED;
        this.arriveTime = arriveTime;
    }
}
