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
import site.minnan.entry.infrastructure.enumerate.Gender;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 旅客实体
 *
 * @author Minnan on 2021/3/15
 */
@ApiModel("旅客实体类")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("entry_traveler")
public class Traveler extends ModifiableEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("旅客姓名")
    private String name;

    @ApiModelProperty("旅客证件号码")
    private String cardNumber;

    @ApiModelProperty("性别")
    private Gender gender;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("联系电话")
    private String phoneNumber;

    @ApiModelProperty("入境时间")
    private Timestamp entryTime;

    @ApiModelProperty("登车时间")
    private Timestamp boardingTime;

    @ApiModelProperty("车次id")
    private Integer trainRecordId;

    @ApiModelProperty("送往酒店时间（入住时间）")
    private Timestamp arrivalTime;

    @ApiModelProperty("酒店id")
    private Integer hotelId;

    @ApiModelProperty("酒店名称")
    private String hotelName;

    @ApiModelProperty("隔离开始时间")
    private Timestamp quarantineStartTime;

    @ApiModelProperty("隔离结束时间")
    private Timestamp quarantineEndTime;

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
