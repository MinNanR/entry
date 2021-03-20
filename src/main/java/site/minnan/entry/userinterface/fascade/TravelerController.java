package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.TravelerService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.HotelData;
import site.minnan.entry.domain.vo.traveler.NationalityStatistics;
import site.minnan.entry.domain.vo.traveler.TravelerArchive;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
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

    @PreAuthorize("hasAnyAuthority('ADMIN','PORT_USER')")
    @ApiOperation("添加旅客")
    @PostMapping("addTraveler")
    public ResponseEntity<?> addTraveler(@RequestBody @Valid AddTravelerDTO dto) {
        travelerService.addTraveler(dto);
        return ResponseEntity.success();
    }

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
    public ResponseEntity<ListQueryVO<TravelerVO>> getNotQuarantineTravelerList(@RequestBody @Valid ListQueryDTO dto){
        ListQueryVO<TravelerVO> vo = travelerService.getNotQuarantineTravelerList(dto);
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HOTEL_USER')")
    @ApiOperation("开始隔离")
    @PostMapping("startQuarantine")
    public ResponseEntity<?> startQuarantine(@RequestBody @Valid StartQuarantineDTO dto) {
        travelerService.startQuarantine(dto);
        return ResponseEntity.success();
    }

    @ApiOperation("结束隔离")
    @PostMapping("endQuarantine/{id}")
    public ResponseEntity<?> endQuarantine(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                           @NotNull(message = "未指定结束隔离的旅客") Integer travelerId) {
        return ResponseEntity.success();
    }

    @ApiOperation("酒店数据面板")
    @PostMapping("getHotelData")
    public ResponseEntity<HotelData> getHotelData() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("人员国籍归属分析")
    @PostMapping("getNationalityStatistics")
    public ResponseEntity<NationalityStatistics> getNationalityStatistics() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("旅客个人档案")
    @PostMapping("getPersonalArchive/{id}")
    public ResponseEntity<TravelerArchive> getPersonalArchive(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                                              @NotNull(message = "未指定结束隔离的旅客") Integer travelerId) {
        TravelerArchive travelerArchive = travelerService.getTravelerArchive(travelerId);
        return ResponseEntity.success(travelerArchive);
    }
}
