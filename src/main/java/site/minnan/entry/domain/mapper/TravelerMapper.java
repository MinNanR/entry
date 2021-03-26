package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.Traveler;
import site.minnan.entry.domain.vo.traveler.HotelData;
import site.minnan.entry.userinterface.dto.traveler.GetHotelDataDTO;

import java.util.Collection;
import java.util.List;
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

    /**
     * 酒店数据面板
     *
     * @param dto
     * @return
     */
    HotelData getHotelData(GetHotelDataDTO dto);
}
