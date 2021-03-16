package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.userinterface.response.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "通用接口")
@RestController
@RequestMapping("/entry/common")
public class CommonController {

    @ApiOperation("角色下拉框")
    @PostMapping("getRoleDropDown")
    public ResponseEntity<List<DropDownBox>> getRoleDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(Role.values())
                .map(e -> new DropDownBox(e.getValue(), e.getRoleName()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }
}
