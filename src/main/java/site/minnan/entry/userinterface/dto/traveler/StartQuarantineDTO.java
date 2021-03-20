package site.minnan.entry.userinterface.dto.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * @author Minnan
 */
@ApiModel("旅客开始隔离参数")
@Data
public class StartQuarantineDTO {

    @ApiModelProperty(value = "隔离开始时间（格式：yyyy-MM-dd）", required = true, example = "2021-03-01")
    @NotEmpty(message = "隔离开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "旅客id数组", required = true, example = "[1,2,3]")
    @NotEmpty(message = "未选择要隔离的旅客")
    private Collection<Integer> travelerIds;
}
