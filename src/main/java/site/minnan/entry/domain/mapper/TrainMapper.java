package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.entity.TrainData;
import site.minnan.entry.userinterface.dto.train.GetTrainListDTO;

import java.util.List;

@Mapper
@Repository
public interface TrainMapper extends BaseMapper<Train> {

    List<TrainData> getTrainList(GetTrainListDTO dto);

    List<TrainData> getNotArrivedTrainList(@Param("carNumber") String carNumber, @Param("hotelId") Integer hotelId);

    List<TrainData> getArrivedTrainList(GetTrainListDTO param);
}
