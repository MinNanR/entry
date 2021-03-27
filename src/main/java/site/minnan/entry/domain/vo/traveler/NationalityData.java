package site.minnan.entry.domain.vo.traveler;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("国籍数据")
@Data
@AllArgsConstructor
public class NationalityData {

    private String name;

    private Integer value;
}
