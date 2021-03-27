package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel("地区统计")
@Data
@AllArgsConstructor
public class NationalityStatics {

    @ApiModelProperty("人数总数")
    private Integer totalCount;

    @ApiModelProperty("国籍统计")
    private List<NationalityData> nationalityData;

    @ApiModelProperty("省份统计")
    private AreaData provinceData;
}
