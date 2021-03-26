package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("获取酒店数据面板")
@Data
public class GetHotelDataDTO {

    @ApiModelProperty(value = "酒店id", example = "1")
    private Integer hotelId;

    @ApiModelProperty(value = "入境开始时间（yyyy-MM-dd HH:mm）", example = "2021-03-20 09:00")
    private String startTime;

    @ApiModelProperty(value = "入境结束时间（yyyy-MM-dd HH:mm）", example = "2021-03-20 09:00")
    private String endTime;
}
