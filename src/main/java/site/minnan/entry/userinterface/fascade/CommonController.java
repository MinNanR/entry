package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.infrastructure.enumerate.TemperatureRecordStatus;
import site.minnan.entry.infrastructure.enumerate.TrainStatus;
import site.minnan.entry.infrastructure.enumerate.TravelerStatus;
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

    @ApiOperation("车次状态下拉框")
    @PostMapping("getTrainStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTrainStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TrainStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @ApiOperation("旅客状态下拉框")
    @PostMapping("getTravelerStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTravelerStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TravelerStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @ApiOperation("体温测量记录状态下拉框")
    @PostMapping("getTemperatureRecordStatusDropDown")
    public ResponseEntity<List<DropDownBox>> getTemperatureRecordStatusDropDown() {
        List<DropDownBox> dropDownBoxList = Stream.of(TemperatureRecordStatus.values())
                .map(e -> new DropDownBox(e.getValue(), e.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.success(dropDownBoxList);
    }

    @ApiOperation("酒店下拉框")
    @PostMapping("getHotelDropDown")
    public ResponseEntity<List<DropDownBox>> getHotelDropDown() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("口岸下拉框")
    @PostMapping("getPortDropDown")
    public ResponseEntity<List<DropDownBox>> getPortDropDown() {
        return ResponseEntity.success(null);
    }
}
