package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Minnan on 2021/03/16
 */
@ApiModel("个人档案")
@Data
public class TravelerInfoVO {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "姓名", example = "王帅")
    private String name;

    @ApiModelProperty(value = "性别", example = "男")
    private String gender;

    @ApiModelProperty(value = "年龄", example = "24")
    private Integer age;

    @ApiModelProperty(value = "证件号", example = "441933199704122345")
    private String cardNumber;

    @ApiModelProperty(value = "国籍", example = "中国")
    private String nationality;

    @ApiModelProperty(value = "入境时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 16:00")
    private String createTime;

    @ApiModelProperty(value = "登车时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 17:00")
    private String boardingTime;

    @ApiModelProperty(value = "抵达酒店时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 18:00")
    private String arrivalTime;

    @ApiModelProperty(value = "集中隔离时间（格式：yyyy-MM-dd）", example = "2021-03-17")
    private String quarantineStartTime;

    @ApiModelProperty(value = "解除隔离时间（格式：yyyy-MM-dd", example = "2021-04-01")
    private String quarantineEndTime;

    @ApiModelProperty(value = "集中隔离酒店名称", example = "荔湾酒店")
    private String quarantineHotelName;
}
