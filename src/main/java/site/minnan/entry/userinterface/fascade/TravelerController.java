package site.minnan.entry.userinterface.fascade;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.TravelerService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.HotelData;
import site.minnan.entry.domain.vo.traveler.NationalityStatistics;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
import site.minnan.entry.userinterface.dto.traveler.AddTravelerDTO;
import site.minnan.entry.userinterface.dto.traveler.GetTravelerListDTO;
import site.minnan.entry.userinterface.dto.traveler.StartQuarantineDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

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

    @ApiOperation("删除旅客")
    @PostMapping("deleteTraveler/{id}")
    public ResponseEntity<?> deleteTraveler(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                            @NotNull(message = "未指定删除的旅客") Integer travelerId) {
        travelerService.deleteTraveler(travelerId);
        return ResponseEntity.success();
    }

    @ApiOperation("查询指定车次上的乘客")
    @PostMapping("getTravelerListByTrain/{id}")
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerListByTrain(
            @ApiParam(value = "车次id", required = true, example = "1") @Valid @PathVariable("id") @NotNull(message =
                    "未指定查询的车次") Integer trainId) {
        return ResponseEntity.success(null);
    }

    @ApiOperation("查询指定酒店的在店旅客")
    @PostMapping("getTravelerListByHotel")
    public ResponseEntity<ListQueryVO<TravelerVO>> getTravelerListByHotel() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("开始隔离")
    @PostMapping("startQuarantine")
    public ResponseEntity<?> startQuarantine(@RequestBody @Valid StartQuarantineDTO dto) {
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
    public ResponseEntity<?> getPersonalArchive(@ApiParam(value = "旅客id", required = true, example = "1") @Valid @PathVariable("id")
                                                @NotNull(message = "未指定结束隔离的旅客") Integer travelerId) {
        return ResponseEntity.success();
    }
}
