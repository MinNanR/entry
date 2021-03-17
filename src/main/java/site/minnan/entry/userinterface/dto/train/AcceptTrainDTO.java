package site.minnan.entry.userinterface.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;

@ApiModel("车次转入酒店参数")
@Data
public class AcceptTrainDTO {

    @ApiModelProperty(value = "车次id数组", required = true, example = "[1,2,3]")
    private Collection<Integer> trainIds;
}
