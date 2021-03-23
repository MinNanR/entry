package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.application.service.LogService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.log.LogVO;
import site.minnan.entry.userinterface.dto.log.GetLogListDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "日志")
@RestController
@RequestMapping("/entry/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getLogList")
    public ResponseEntity<ListQueryVO<LogVO>> getLogList(@RequestBody @Valid GetLogListDTO dto){
        ListQueryVO<LogVO> vo = logService.getLogList(dto);
        return ResponseEntity.success(vo);
    }
}
