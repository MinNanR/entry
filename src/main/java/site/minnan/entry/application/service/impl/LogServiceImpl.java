package site.minnan.entry.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.service.LogService;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.entity.Log;
import site.minnan.entry.domain.mapper.LogMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.log.LogVO;
import site.minnan.entry.infrastructure.annocation.OperateLog;
import site.minnan.entry.userinterface.dto.log.GetLogListDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 记录日志
     *
     * @param operateLog
     * @param ip
     */
    @Override
    public void addLog(OperateLog operateLog, String ip) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Log log = Log.builder()
                .username(jwtUser.getUsername())
                .ip(ip)
                .time(new Timestamp(System.currentTimeMillis()))
                .module(operateLog.module())
                .operation(operateLog.operation().operationName())
                .content(operateLog.content())
                .build();
        logMapper.insert(log);
    }

    /**
     * 获取日志列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<LogVO> getLogList(GetLogListDTO dto) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getUsername()).ifPresent(s -> queryWrapper.like("username", s));
        Optional.ofNullable(dto.getOperation()).ifPresent(s -> queryWrapper.eq("operation", s));
        queryWrapper.orderByDesc("time");
        Page<Log> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Log> page = logMapper.selectPage(queryPage, queryWrapper);
        List<LogVO> list = page.getRecords().stream().map(LogVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }
}
