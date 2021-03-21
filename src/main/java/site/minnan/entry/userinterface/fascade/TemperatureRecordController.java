package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.application.service.TemperatureService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.userinterface.dto.temperture.GetTemperatureRecordDTO;
import site.minnan.entry.userinterface.dto.temperture.RecordTemperatureDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "体温测量")
@RestController
@RequestMapping("/entry/temperature")
public class TemperatureRecordController {

    @Autowired
    private TemperatureService temperatureService;

    @ApiOperation("获取体温测量记录")
    @PostMapping("getTemperatureRecordList")
    public ResponseEntity<ListQueryVO<TemperatureRecordVO>> getTemperatureRecordList(@RequestBody @Valid GetTemperatureRecordDTO dto) {
        ListQueryVO<TemperatureRecordVO> vo = temperatureService.getTemperatureRecordList(dto);
        return ResponseEntity.success(vo);
    }

    @ApiOperation("记录旅客体温")
    @PostMapping("recordTemperature")
    public ResponseEntity<?> recordTemperature(@RequestBody @Valid List<RecordTemperatureDTO> dto) {
        temperatureService.recordTemperature(dto);
        return ResponseEntity.success();
    }

}
