package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.application.service.LocationService;
import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.location.LocationVO;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationDropDownDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationListDTO;
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "位置")
@RestController
@RequestMapping("/entry/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("更新位置信息")
    @PostMapping("updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody @Valid UpdateLocationDTO dto) {
        locationService.updateLocation(dto);
        return ResponseEntity.success();
    }

    @ApiOperation("位置下拉框")
    @PostMapping("getLocationDropDown")
    public ResponseEntity<List<DropDownBox>> getLocationDropDown(@RequestBody GetLocationDropDownDTO dto) {
        List<DropDownBox> dropDownBox = locationService.getDropDownBox(dto);
        return ResponseEntity.success(dropDownBox);
    }
}
