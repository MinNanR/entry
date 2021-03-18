package site.minnan.entry.domain.vo.traveler;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;

import java.util.List;

@ApiModel("旅客个人档案")
@Data
public class TravelerArchive {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "姓名", example = "王帅")
    private String name;

    @ApiModelProperty(value = "性别", example = "男")
    private String gender;

    @ApiModelProperty(value = "年龄", example = "24")
    private Integer age;

    @ApiModelProperty(value = "国籍", example = "中国")
    private String nationality;

    @ApiModelProperty(value = "证件号", example = "441900199703041234")
    private String cardNumber;

    @ApiModelProperty(value = "生日(yyy-MM-dd)", example = "1997-03-04")
    private String birthday;

    @ApiModelProperty(value = "入境口岸",example = "横琴口岸")
    private String portName;

    @ApiModelProperty(value = "集中隔离酒店",example = "荔湾酒店")
    private String hotelName;

    @ApiModelProperty(value = "入境时间(yyyy-MM-dd HH:mm)", example = "2021-03-18 15:00")
    private String entryTime;

    @ApiModelProperty(value = "开始隔离时间(yyyy-MM-dd)",example = "2021-03-18")
    private String quarantineStartTime;

    @ApiModelProperty(value = "结束隔离时间(yyyy-MM-dd)",example = "2021-04-01")
    private String quarantineEndTime;

    @ApiModelProperty(value = "体温测量记录")
    List<TemperatureRecordVO> temperatureRecordList;

    public TravelerArchive(Traveler traveler){
        this.id = traveler.getId();
        this.name = traveler.getName();
        this.gender = traveler.getGender().getGender();
        this.age = traveler.getAge();
        this.nationality = traveler.getNationality();
        this.cardNumber = traveler.getCardNumber();
        this.birthday = DateUtil.formatDate(traveler.getBirthday());
        this.entryTime = DateUtil.format(traveler.getEntryTime(), "yyyy-MM-dd HH:mm");
    }


}
