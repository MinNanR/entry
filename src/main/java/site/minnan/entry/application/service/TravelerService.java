package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
import site.minnan.entry.userinterface.dto.traveler.AddTravelerDTO;
import site.minnan.entry.userinterface.dto.traveler.GetTravelerListDTO;

/**
 * @author Minnan on 2021/3/18
 */
public interface TravelerService {

    /**
     * 添加旅客
     *
     * @param dto
     */
    void addTraveler(AddTravelerDTO dto);

    /**
     * 人员明细
     *
     * @param dto
     * @return
     */
    ListQueryVO<TravelerVO> getTravelerList(GetTravelerListDTO dto);

    /**
     * 删除旅客
     *
     * @param travelerId 旅客id
     */
    void deleteTraveler(Integer travelerId);
}
