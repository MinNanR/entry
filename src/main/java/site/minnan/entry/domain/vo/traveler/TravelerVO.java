package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("旅客列表查询数据")
@Data
public class TravelerVO {

    @ApiModelProperty(value = "旅客姓名", example = "王帅")
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

    @ApiModelProperty(value = "送往的酒店", example = "荔湾酒店")
    private String hotelName;

    @ApiModelProperty(value = "状态", example = "隔离中")
    private String status;

    @ApiModelProperty(value = "状态码",example = "QUARANTINE",allowableValues = "ENTRY-已入境,TRANSPORTING-运输中," +
            "ARRIVE-已抵达酒店,QUARANTINE-隔离中,RELEASED-已解除隔离")
    private String statusCode;

}
