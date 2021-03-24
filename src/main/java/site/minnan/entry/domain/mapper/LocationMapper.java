package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Location;

/**
 *
 * @author Minnan on 2021/3/17
 */
@Mapper
@Repository
public interface LocationMapper extends BaseMapper<Location> {

    @Select("select id from entry_traveler where port_id = #{locationId} limit 1")
    Integer checkPortUsed(@Param("locationId") Integer locationId);

    @Select("select id from entry_train where hotel_id = #{locationId} limit 1")
    Integer checkHotelUsed(@Param("locationId")Integer locationId);
}
