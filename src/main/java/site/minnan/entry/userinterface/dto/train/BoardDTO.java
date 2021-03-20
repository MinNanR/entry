package site.minnan.entry.userinterface.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@ApiModel("旅客登车参数")
@Data
public class BoardDTO {

    @ApiModelProperty(value = "车次id", required = true, example = "1")
    @NotNull(message = "未指定车次")
    public Integer trainId;

    @ApiModelProperty(value = "需要登车的旅客id", required = true, example = "[1,2,3]")
    @NotEmpty(message = "至少一名旅客登车")
    public Collection<Integer> travelerIds;
}
