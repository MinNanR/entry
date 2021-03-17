package site.minnan.entry.domain.vo.location;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.aggregate.Location;

/**
 * 位置列表展示数据
 *
 * @author Minnan on 2021/3/16
 */
@ApiModel("位置列表展示数据")
@Builder
@Data
public class LocationVO {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "位置名称", example = "横琴口岸")
    private String locationName;

    @ApiModelProperty(value = "位置类型", example = "口岸")
    private String locationType;

    @ApiModelProperty(value = "省份", example = "广东")
    private String province;

    @ApiModelProperty(value = "城市", example = "珠海")
    private String city;

    @ApiModelProperty(value = "地理位置", example = "香洲区环岛东路")
    private String locationAddress;

    @ApiModelProperty(value = "更新时间（格式：yyyy-MM-dd HH:mm", example = "2021-03-16 09:00")
    private String updateTime;

    public static LocationVO assemble(Location location) {
        return builder()
                .id(location.getId())
                .locationName(location.getName())
                .locationType(location.getType().getType())
                .province(location.getProvince())
                .city(location.getCity())
                .locationAddress(location.getAddress())
                .updateTime(DateUtil.format(location.getUpdateTime(), "yyyy-MM-dd HH:mm"))
                .build();
    }
}
