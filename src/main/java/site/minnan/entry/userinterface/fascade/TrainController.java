package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.application.service.TrainService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.ArrivedTrainVO;
import site.minnan.entry.domain.vo.train.ArrivingTrainVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.userinterface.dto.train.AcceptTrainDTO;
import site.minnan.entry.userinterface.dto.train.AddTrainDTO;
import site.minnan.entry.userinterface.dto.train.GetTrainListDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "车次")
@RestController
@RequestMapping("/entry/trainRecord")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @ApiOperation("创建车次")
    @PostMapping("addTrain")
    public ResponseEntity<?> addTrain(@RequestBody @Valid AddTrainDTO dto) {
        trainService.addTrain(dto);
        return ResponseEntity.success();
    }

    @ApiOperation("口岸人员-查询车次列表")
    @PostMapping("getTrainList/port")
    public ResponseEntity<ListQueryVO<TrainVO>> getTrainList(@RequestBody @Valid GetTrainListDTO dto) {
        ListQueryVO<TrainVO> vo = trainService.getTrainList(dto);
        return ResponseEntity.success(vo);
    }

    @ApiOperation("酒店人员-查询即将抵达的车次列表")
    @PostMapping("getTrainList/arriving")
    public ResponseEntity<ListQueryVO<ArrivingTrainVO>> getArrivingTrainList() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("酒店人员-查询已转入该酒店的车次列表")
    @PostMapping("getTrainList/arrived")
    public ResponseEntity<ListQueryVO<ArrivedTrainVO>> getArrivedTrainList(@RequestBody @Valid GetTrainListDTO dto) {
        return ResponseEntity.success(null);
    }

    @ApiOperation("酒店人员-车次转入")
    @PostMapping("acceptTrain")
    public ResponseEntity<?> acceptTrain(@RequestBody @Valid AcceptTrainDTO dto) {
        return ResponseEntity.success();
    }
}
