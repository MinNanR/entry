package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Location;

/**
 *
 * @author Minnan on 2021/3/17
 */
@Mapper
@Repository
public interface LocationMapper extends BaseMapper<Location> {
}
