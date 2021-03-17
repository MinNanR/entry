package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.location.LocationVO;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationListDTO;
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "位置接口")
@RestController
@RequestMapping("/entry/location")
public class LocationController {

    @ApiOperation("添加位置")
    @PostMapping("addLocation")
    public ResponseEntity<?> addLocation(@RequestBody @Valid AddLocationDTO dto) {
        return ResponseEntity.success();
    }

    @ApiOperation("获取位置列表")
    @PostMapping("getLocationList")
    public ResponseEntity<ListQueryVO<LocationVO>> getLocationVO(@RequestBody @Valid GetLocationListDTO dto) {

        return null;
    }

    @ApiOperation("更新位置信息")
    @PostMapping("updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody @Valid UpdateLocationDTO dto) {
        return ResponseEntity.success();
    }


}