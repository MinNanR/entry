package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.userinterface.dto.temperture.GetTemperatureRecordDTO;
import site.minnan.entry.userinterface.dto.temperture.RecordTemperatureDTO;

import java.util.List;

/**
 * @author Minnan
 */
public interface TemperatureService {

    /**
     * 获取体温记录列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<TemperatureRecordVO> getTemperatureRecordList(GetTemperatureRecordDTO dto);

    /**
     * 记录乘客体温
     *
     * @param dtoList
     */
    void recordTemperature(List<RecordTemperatureDTO> dtoList);
}
