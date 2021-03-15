package site.minnan.entry.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.ModifiableEntity;

import java.sql.Timestamp;

@ApiModel("车次")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@TableName("entry_train_record")
public class TrainRecord extends ModifiableEntity {

    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("车牌号")
    private String cardNumber;

    @ApiModelProperty("司机姓名")
    private String driverName;

    @ApiModelProperty("司机电话号码")
    private String driverPhone;

    @ApiModelProperty("送往的酒店id")
    private Integer hotelId;

    @ApiModelProperty("送往的酒店名称")
    private String hotelName;

    @ApiModelProperty("出发时间")
    private Timestamp departureTime;

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
