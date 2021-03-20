package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Traveler;

import java.util.Collection;
import java.util.Map;

@Mapper
@Repository
public interface TravelerMapper extends BaseMapper<Traveler> {

    /**
     * 设置旅客已到达
     *
     * @param travelers
     */
    void travelerArrive(@Param("travelers") Collection<Traveler> travelers);
}
