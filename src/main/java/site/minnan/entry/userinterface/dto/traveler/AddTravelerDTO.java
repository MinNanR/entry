package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加旅客参数
 *
 * @author Minnan on 2021/03/16
 */
@ApiModel("添加旅客参数")
@Data
public class AddTravelerDTO {

    @ApiModelProperty(value = "旅客姓名", required = true, example = "王帅")
    @NotEmpty(message = "旅客姓名不能为空")
    private String name;

    @ApiModelProperty(value = "证件号", required = true, example = "441933199704122345", dataType = "String")
    @NotEmpty(message = "证件号不能为空")
    private String cardNumber;

    @ApiModelProperty(value = "性别", required = true, allowableValues = "MALE, FEMALE", example = "MALE")
    @NotEmpty(message = "性别不能为空")
    private String gender;

    @ApiModelProperty(value = "出生日期（格式：yyyy-MM-dd）", required = true, example = "1997-04-12")
    private String birthday;

    @ApiModelProperty(value = "国籍", required = true, example = "中国")
    @NotEmpty(message = "国籍不能为空")
    private String nationality;

    @ApiModelProperty(value = "省份", example = "广东")
    private String province;

    @ApiModelProperty(value = "入境口岸id", required = true, example = "4")
    @NotNull(message = "未指定入境口岸")
    private Integer portId;

    @ApiModelProperty(value = "入境口岸名称", required = true, example = "横琴口岸")
    @NotEmpty(message = "未指定入境口岸")
    private String portName;
}
