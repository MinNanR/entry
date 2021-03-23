package site.minnan.entry.domain.vo.log;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import site.minnan.entry.domain.entity.Log;

@ApiModel("日志显示数据")
@Builder
@Data
public class LogVO {

    private String username;

    private String operation;

    private String module;

    private String operateContent;

    private String ip;

    private String createTime;

    public static LogVO assemble(Log log) {
        return LogVO.builder()
                .username(log.getUsername())
                .operation(log.getOperation())
                .module(log.getModule())
                .operateContent(log.getContent())
                .ip(log.getIp())
                .createTime(DateUtil.formatDateTime(log.getTime()))
                .build();
    }

    public String[] getLogInfo(Integer ordinal) {
        return new String[]{String.valueOf(ordinal), username, ip, operation, createTime, module, operateContent};
    }

}