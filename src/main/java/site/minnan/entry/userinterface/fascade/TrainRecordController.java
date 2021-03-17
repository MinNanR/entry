package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.userinterface.dto.trainrecord.AddTrainRecordDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "车次")
@RestController
@RequestMapping("/entry/trainRecord")
public class TrainRecordController {

    @ApiOperation("创建车次")
    @PostMapping("addTranRecord")
    public ResponseEntity<?> addTrainRecord(@RequestBody @Valid AddTrainRecordDTO dto) {
        return ResponseEntity.success();
    }
}
