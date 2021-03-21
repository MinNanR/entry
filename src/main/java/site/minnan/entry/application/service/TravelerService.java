package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.traveler.TravelerArchive;
import site.minnan.entry.domain.vo.traveler.TravelerVO;
import site.minnan.entry.userinterface.dto.ListQueryDTO;
import site.minnan.entry.userinterface.dto.traveler.*;

import java.util.Collection;

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

    /**
     * 获取旅客档案
     *
     * @param travelerId
     * @return
     */
    TravelerArchive getTravelerArchive(Integer travelerId);

    /**
     * 获取未登车的旅客下拉框
     *
     * @param dto
     * @return
     */
    ListQueryVO<TravelerVO> getNotBoardedTraverList(GetNotBoardedTravelerListDTO dto);

    /**
     * 根据车次获取车上乘客
     *
     * @param trainId
     * @return
     */
    ListQueryVO<TravelerVO> getTravelerListByTrain(GetTravelerListByTrainDTO trainId);

    /**
     * 获取当前在店的旅客
     *
     * @param dto
     * @return
     */
    ListQueryVO<TravelerVO> getTravelerListInHotel(GetTravelerInHotelDTO dto);

    /**
     * 获取未开始隔离的旅客列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<TravelerVO> getNotQuarantineTravelerList(ListQueryDTO dto);

    /**
     * 开始隔离
     *
     * @param dto
     */
    void startQuarantine(StartQuarantineDTO dto);

    /**
     * 结束隔离
     *
     * @param travelerId 旅客id
     */
    void endQuarantine(Integer travelerId);
}
