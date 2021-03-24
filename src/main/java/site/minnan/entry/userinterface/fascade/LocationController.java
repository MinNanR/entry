package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.LocationService;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.location.LocationVO;
import site.minnan.entry.infrastructure.annocation.OperateLog;
import site.minnan.entry.infrastructure.enumerate.Operation;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationDropDownDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationListDTO;
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "位置")
@RestController
@RequestMapping("/entry/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @OperateLog(operation = Operation.ADD, module = "位置", content = "添加位置")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("添加位置")
    @PostMapping("addLocation")
    public ResponseEntity<?> addLocation(@RequestBody @Valid AddLocationDTO dto) {
        locationService.addLocation(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("获取位置列表")
    @PostMapping("getLocationList")
    public ResponseEntity<ListQueryVO<LocationVO>> getLocationVO(@RequestBody @Valid GetLocationListDTO dto) {
        ListQueryVO<LocationVO> vo = locationService.getLocationList(dto);
        return ResponseEntity.success(vo);
    }

    @OperateLog(operation = Operation.UPDATE, module = "位置", content = "修改位置信息")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("更新位置信息")
    @PostMapping("updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody @Valid UpdateLocationDTO dto) {
        locationService.updateLocation(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("位置下拉框")
    @PostMapping("getLocationDropDown")
    public ResponseEntity<List<DropDownBox>> getLocationDropDown(@RequestBody GetLocationDropDownDTO dto) {
        List<DropDownBox> dropDownBox = locationService.getDropDownBox(dto);
        return ResponseEntity.success(dropDownBox);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("删除口岸")
    @PostMapping("deleteLocation/{id}")
    public ResponseEntity<?> deleteLocation(@ApiParam(value = "位置id", required = true, example = "1") @Valid @PathVariable("id") @NotNull(message =
            "未指定删除的口岸") Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.success();
    }
}
