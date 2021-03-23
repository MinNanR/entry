package site.minnan.entry.domain.vo.traveler;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel("人员波动分析")
@Data
public class NumberTrendData {

    @ApiModelProperty("入境人数集合")
    private List<Integer> peopleNumberList;

    @ApiModelProperty("日期集合")
    private List<String> dateList;

    public NumberTrendData(){
        peopleNumberList = new ArrayList<>();
        dateList = new ArrayList<>();
    }

    public void add(int number, Date date){
        peopleNumberList.add(number);
        dateList.add(DateUtil.formatDate(date));
    }
}
