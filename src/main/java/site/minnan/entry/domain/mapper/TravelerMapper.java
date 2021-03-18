package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Traveler;

import java.util.Map;

@Mapper
@Repository
public interface TravelerMapper extends BaseMapper<Traveler> {
}
