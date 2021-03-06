package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.TravelerService;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.*;
import site.minnan.entry.infrastructure.annocation.OperateLog;
import site.minnan.entry.infrastructure.enumerate.Operation;
import site.minnan.entry.userinterface.dto.ListQueryDTO;
import site.minnan.entry.userinterface.dto.traveler.*;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "旅客")
@RestController
@RequestMapping("/entry/traveler")
public class TravelerController {

    @Autowired
    private TravelerService travelerService;

    @OperateLog(operation = Operation.ADD, module = "旅客",content = "添加旅客")
    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("添加旅客")
    @PostMapping("addTraveler")
    public ResponseEntity<?> addTraveler(@RequestBody @Valid AddTravelerDTO dto) {
        travelerService.addTraveler(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("人员明细")
    @PostMapping("getTravelerList")
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerList(@RequestBody @Valid GetTravelerListDTO dto) {
        ListQueryVO<TravelerVO> vo = travelerService.getTravelerList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("获取未登车的旅客")
    @PostMapping("getNotBoardedTravelerList")
    public ResponseEntity<ListQueryVO<TravelerVO>> getNotBoardedTravelerList(@RequestBody @Valid GetNotBoardedTravelerListDTO dto) {
        ListQueryVO<TravelerVO> vo = travelerService.getNotBoardedTraverList(dto);
        return ResponseEntity.success(vo);
    }

    @OperateLog(operation = Operation.DELETE, module = "旅客",content = "删除旅客")
    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("删除旅客")
    @PostMapping("deleteTraveler/{id}")
    public ResponseEntity<?> deleteTraveler(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                            @NotNull(message = "未指定删除的旅客") Integer travelerId) {
        travelerService.deleteTraveler(travelerId);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("查询指定车次上的乘客")
    @PostMapping("getTravelerListByTrain")
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerListByTrain(@RequestBody @Valid GetTravelerListByTrainDTO dto) {
        ListQueryVO<TravelerVO> vo = travelerService.getTravelerListByTrain(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("查询当前在酒店的旅客")
    @PostMapping("getTravelerList/hotel")
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerListInHotel(@RequestBody @Valid GetTravelerInHotelDTO dto) {
        ListQueryVO<TravelerVO> vo = travelerService.getTravelerListInHotel(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("查询未开始隔离的旅客")
    @PostMapping("getNotQuarantineTravelerList")
    public ResponseEntity<ListQueryVO<TravelerVO>> getNotQuarantineTravelerList(@RequestBody GetNotQuarantineTravelerListDTO dto){
        ListQueryVO<TravelerVO> vo = travelerService.getNotQuarantineTravelerList(dto);
        return ResponseEntity.success(vo);
    }

    @OperateLog(operation = Operation.UPDATE, module = "旅客",content = "隔离旅客")
    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("开始隔离")
    @PostMapping("startQuarantine")
    public ResponseEntity<?> startQuarantine(@RequestBody @Valid StartQuarantineDTO dto) {
        travelerService.startQuarantine(dto);
        return ResponseEntity.success();
    }

    @OperateLog(operation = Operation.UPDATE, module = "旅客",content = "接触隔离旅客")
    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("结束隔离")
    @PostMapping("endQuarantine/{id}")
    public ResponseEntity<?> endQuarantine(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                           @NotNull(message = "未指定结束隔离的旅客") Integer travelerId) {
        travelerService.endQuarantine(travelerId);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("酒店数据面板")
    @PostMapping("getHotelData")
    public ResponseEntity<HotelData> getHotelData(@RequestBody @Valid GetHotelDataDTO dto) {
        HotelData vo = travelerService.getHotelData(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("人员国籍归属分析")
    @PostMapping("getNationalityStatistics")
    public ResponseEntity<NationalityStatics> getNationalityStatistics() {
        NationalityStatics vo = travelerService.getNationalityStatics();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("旅客个人档案")
    @PostMapping("getPersonalArchive/{id}")
    public ResponseEntity<TravelerArchive> getPersonalArchive(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                                              @NotNull(message = "未指定查询的旅客") Integer travelerId) {
        TravelerArchive travelerArchive = travelerService.getTravelerArchive(travelerId);
        return ResponseEntity.success(travelerArchive);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("获取人员数量波动分析")
    @PostMapping("getNumberTrend")
    public ResponseEntity<NumberTrendData> getNumberTrend(){
        NumberTrendData vo = travelerService.getNumberTrend();
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER','HOTEL_USER')")
    @ApiOperation("获取已接受的旅客数量")
    @PostMapping("getAcceptedTravelerCount")
    public ResponseEntity<Integer> getAcceptedTravelerCount(){
        int count = travelerService.getAcceptedTravelerCount();
        return ResponseEntity.success(count);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("获取正在隔离且隔离时间大于14天的旅客")
    @PostMapping("getQuarantineTravelerList")
    public ResponseEntity<ListQueryVO<TravelerVO>> getQuarantineTravelerList(@RequestBody @Valid GetQuarantineTravelerListDTO dto){
        ListQueryVO<TravelerVO> vo = travelerService.getQuarantineTravelerList(dto);
        return ResponseEntity.success(vo);
    }
}
