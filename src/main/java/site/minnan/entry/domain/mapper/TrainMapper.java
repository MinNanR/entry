package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.entity.TrainData;

import java.util.List;

@Mapper
@Repository
public interface TrainMapper extends BaseMapper<Train> {

    List<TrainData> getTrainList(@Param("carNumber") String carNumber, @Param("start") Integer start, @Param(
            "pageSize") Integer pageSize);
}
