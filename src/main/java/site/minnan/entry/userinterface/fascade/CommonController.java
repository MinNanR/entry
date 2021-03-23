package site.minnan.entry.userinterface.fascade;

import cn.hutool.core.map.MapBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.infrastructure.enumerate.*;
import site.minnan.entry.userinterface.response.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "通用接口")
@RestController
@RequestMapping("/entry/common")
public class CommonController {

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("角色下拉框")
    @PostMapping("getRoleDropDown")
    public ResponseEntity<List<DropDownBox>> getRoleDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(Role.values())
                .map(e -> new DropDownBox(e.getValue(), e.getRoleName()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("车次状态下拉框")
    @PostMapping("getTrainStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTrainStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TrainStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("旅客状态下拉框")
    @PostMapping("getTravelerStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTravelerStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TravelerStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("体温测量记录状态下拉框")
    @PostMapping("getTemperatureRecordStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTemperatureRecordStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TemperatureRecordStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getLogOperation")
    public ResponseEntity<ListQueryVO<Map<Object, Object>>> getLogOperation() {
        List<Map<Object, Object>> list = Arrays.stream(Operation.values())
                .map(e -> MapBuilder.create().put("key", e.operationName()).put("value", e.operationName()).build())
                .collect(Collectors.toList());
        ListQueryVO<Map<Object, Object>> vo = new ListQueryVO<>(list, null);
        return ResponseEntity.success(vo);
    }
}
