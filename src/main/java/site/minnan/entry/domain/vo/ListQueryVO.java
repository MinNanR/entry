package site.minnan.entry.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 列表查询返回值基类
 *
 * @param <T> 列表内容
 * @author Minnan on 2020/12/16
 */
@ApiModel("列表查询基本对象")
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListQueryVO<T> {

    /**
     * 列表
     */
    @ApiModelProperty(value = "查询结果")
    List<T> list;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量", example = "50")
    Long totalCount;

    public ListQueryVO(List<T> list, int totalCount){
        this(list, (long) totalCount);
    }
}