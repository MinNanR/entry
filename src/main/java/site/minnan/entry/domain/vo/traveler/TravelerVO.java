package site.minnan.entry.domain.vo.traveler;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.aggregate.Traveler;

import java.util.Optional;

@ApiModel("旅客列表查询数据")
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelerVO {

    @ApiModelProperty(value = "旅客id", example = "1")
    private Integer id;

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

    @ApiModelProperty(value = "省份", example = "广东")
    private String province;

    @ApiModelProperty(value = "入境时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 16:00")
    private String entryTime;

    @ApiModelProperty(value = "入境口岸名称", example = "横琴口岸")
    private String portName;

    @ApiModelProperty(value = "登车时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 17:00")
    private String boardingTime;

    @ApiModelProperty(value = "抵达酒店时间（格式：yyyy-MM-dd HH:mm）", example = "2021-03-16 18:00")
    private String arrivalTime;

    @ApiModelProperty(value = "送往的酒店", example = "荔湾酒店")
    private String hotelName;

    @ApiModelProperty(value = "状态", example = "隔离中")
    private String status;

    @ApiModelProperty(value = "状态码", example = "QUARANTINE", allowableValues = "ENTRY-已入境,TRANSPORTING-运输中," +
            "ARRIVE-已抵达酒店,QUARANTINE-隔离中,RELEASED-已解除隔离")
    private String statusCode;

    @ApiModelProperty(value = "隔离开始时间", example = "2021-03-26")
    private String quarantineStartTime;

    public static TravelerVO assemble(Traveler traveler) {
        TravelerVOBuilder builder = builder()
                .id(traveler.getId())
                .name(traveler.getName())
                .gender(traveler.getGender().getGender())
                .age(traveler.getAge())
                .cardNumber(traveler.getCardNumber())
                .nationality(traveler.getNationality())
                .province(traveler.getProvince())
                .entryTime(DateUtil.format(traveler.getEntryTime(), "yyyy-MM-dd HH:mm"))
                .portName(traveler.getPortName())
                .status(traveler.getStatus().getStatus())
                .statusCode(traveler.getStatus().getValue());
        Optional.ofNullable(traveler.getBoardingTime()).ifPresent(s -> builder.boardingTime(DateUtil.format(s, "yyyy" +
                "-MM-dd HH:mm")));
        Optional.ofNullable(traveler.getArrivalTime()).ifPresent(s -> builder.hotelName(traveler.getHotelName()));
        Optional.ofNullable(traveler.getQuarantineStartTime()).ifPresent(s -> builder.quarantineStartTime(DateUtil.format(s, "yyyy-MM-dd")));
        return builder.build();
    }

    public static TravelerVO toDropDown(Traveler traveler) {
        return builder()
                .id(traveler.getId())
                .name(traveler.getName())
                .build();
    }
}
