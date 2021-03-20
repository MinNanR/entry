package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.TrainService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.ArrivedTrainVO;
import site.minnan.entry.domain.vo.train.ArrivingTrainVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.userinterface.dto.ListQueryDTO;
import site.minnan.entry.userinterface.dto.train.*;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "车次")
@RestController
@RequestMapping("/entry/trainRecord")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("创建车次")
    @PostMapping("addTrain")
    public ResponseEntity<?> addTrain(@RequestBody @Valid AddTrainDTO dto) {
        trainService.addTrain(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("删除车次")
    @PostMapping("deleteTrain/{id}")
    public ResponseEntity<?> deleteTrain(@ApiParam(value = "车次id", required = true, example = "1") @Valid @PathVariable("id") @NotNull(message =
            "未指定查询的车次") Integer trainId) {
        trainService.deleteTrain(trainId);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("旅客登车")
    @PostMapping("board")
    public ResponseEntity<?> board(@RequestBody @Valid BoardDTO dto) {
        trainService.board(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("车次发车")
    @PostMapping("depart/{id}")
    public ResponseEntity<?> depart(@ApiParam(value = "车次id", required = true, example = "1") @Valid @PathVariable("id") @NotNull(message =
            "未指定查询的车次") Integer trainId) {
        trainService.depart(trainId);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("口岸人员-查询车次列表")
    @PostMapping("getTrainList/port")
    public ResponseEntity<ListQueryVO<TrainVO>> getTrainList(@RequestBody @Valid GetTrainListDTO dto) {
        ListQueryVO<TrainVO> vo = trainService.getTrainList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("酒店人员-查询即将抵达的车次列表")
    @PostMapping("getTrainList/arriving")
    public ResponseEntity<ListQueryVO<ArrivingTrainVO>> getArrivingTrainList(@RequestBody @Valid GetNotArrivedTrainListDTO dto) {
        ListQueryVO<ArrivingTrainVO> vo = trainService.getNotArrivedTrainList(dto);
        return ResponseEntity.success(vo);
    }

    @ApiOperation("酒店人员-查询已转入该酒店的车次列表")
    @PostMapping("getTrainList/arrived")
    public ResponseEntity<ListQueryVO<ArrivedTrainVO>> getArrivedTrainList(@RequestBody @Valid GetTrainListDTO dto) {
        ListQueryVO<ArrivedTrainVO> vo = trainService.getArrivedTrainList(dto);
        return ResponseEntity.success(vo);
    }

    @ApiOperation("酒店人员-车次转入")
    @PostMapping("acceptTrain")
    public ResponseEntity<?> acceptTrain(@RequestBody @Valid AcceptTrainDTO dto) {
        trainService.acceptTrain(dto);
        return ResponseEntity.success();
    }
}
