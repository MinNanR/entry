package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.log.LogVO;
import site.minnan.entry.infrastructure.annocation.OperateLog;
import site.minnan.entry.userinterface.dto.log.GetLogListDTO;

import java.io.OutputStream;

/**
 * @author Minnan on 2021/2/25
 */
public interface LogService {

    /**
     * 记录日志
     *
     * @param operateLog
     * @param ip
     */
    void addLog(OperateLog operateLog, String ip);

    /**
     * 获取日志列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<LogVO> getLogList(GetLogListDTO dto);
//
//    /**
//     * 下载日志
//     *
//     * @param dto
//     */
//    void downloadLog(GetLogListDTO dto, OutputStream outputStream);
}