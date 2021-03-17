package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Minnan on 2021/3/17
 */
@ApiModel
@Data
public class NationalityStatistics {

    @ApiModelProperty(value = "中国籍人数", example = "12345")
    private Integer chineseCount;

    @ApiModelProperty(value = "非中国籍人数", example = "9876")
    private Integer foreignCount;
}
