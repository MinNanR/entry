package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.TemperatureRecord;

@Mapper
@Repository
public interface TemperatureRecordMapper extends BaseMapper<TemperatureRecord> {
}
