package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("地区统计")
@Data
@AllArgsConstructor
public class NationalityStatics {

    @ApiModelProperty("国籍统计")
    private AreaData nationalityData;

    @ApiModelProperty("省份统计")
    private AreaData provinceData;
}
