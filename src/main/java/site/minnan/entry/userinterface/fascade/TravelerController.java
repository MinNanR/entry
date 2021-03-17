package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
import site.minnan.entry.userinterface.dto.traveler.AddTravelerDTO;
import site.minnan.entry.userinterface.dto.traveler.GetTravelerListDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "旅客")
@RestController
@RequestMapping("/entry/traveler")
public class TravelerController {

    @ApiOperation("添加旅客")
    @PostMapping("addTraveler")
    public ResponseEntity<?> addTraveler(@RequestBody @Valid AddTravelerDTO dto) {
        return ResponseEntity.success();
    }

    @ApiOperation("人员明细")
    @PostMapping
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerList(@RequestBody @Valid GetTravelerListDTO dto) {
        return ResponseEntity.success(null);
    }


}
