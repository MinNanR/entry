package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.TemperatureRecord;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.userinterface.dto.temperture.GetTemperatureRecordDTO;
import site.minnan.entry.userinterface.dto.temperture.RecordTemperatureDTO;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface TemperatureRecordMapper extends BaseMapper<TemperatureRecord> {

    @Select("select id id, traveler_id travelerId from entry_temperature_record where datediff(#{today}, create_time) = 0")
    List<TemperatureRecord> getTodayRecord(String today);

    /**
     * 批量插入记录
     *
     * @param records
     */
    void insertBatch(@Param("records") Collection<TemperatureRecord> records);

    /**
     * 填充表格
     *
     * @param dtos
     */
    void filTemperatureRecord(@Param("dtos") Collection<RecordTemperatureDTO> dtos, @Param("time") Timestamp time,
                              @Param("userId") Integer userId, @Param("userName") String userName);


    /**
     * 查询体温测量记录
     *
     * @param dto
     * @return
     */
    List<TemperatureRecordVO> getTemperatureRecordList(GetTemperatureRecordDTO dto);
}
