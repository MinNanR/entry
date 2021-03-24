package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel("国家数据")
@Data
public class AreaData {

    @ApiModelProperty("国籍")
    private List<String> areaList;

    @ApiModelProperty("数量")
    private List<Integer> numberList;

    public AreaData(){
        areaList = new ArrayList<>();
        numberList = new ArrayList<>();
    }

    public void add(String area, Integer number){
        areaList.add(area);
        numberList.add(number);
    }
}
