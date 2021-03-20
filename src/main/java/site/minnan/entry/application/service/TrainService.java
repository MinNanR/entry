package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.ArrivedTrainVO;
import site.minnan.entry.domain.vo.train.ArrivingTrainVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.userinterface.dto.train.*;

/**
 * @author Minnan on 2021/03/18
 */
public interface TrainService {

    /**
     * 添加车次
     *
     * @param dto
     */
    void addTrain(AddTrainDTO dto);

    /**
     * 删除车次
     *
     * @param trainId
     */
    void deleteTrain(Integer trainId);

    /**
     * 获取车次
     *
     * @param dto
     * @return
     */
    ListQueryVO<TrainVO> getTrainList(GetTrainListDTO dto);

    /**
     * 旅客登车
     *
     * @param dto
     */
    void board(BoardDTO dto);

    /**
     * 车次发车
     *
     * @param trainId
     */
    void depart(Integer trainId);

    /**
     * 获取即将抵达的车次列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<ArrivingTrainVO> getNotArrivedTrainList(GetNotArrivedTrainListDTO dto);

    /**
     * 车次转入
     *
     * @param dto
     */
    void acceptTrain(AcceptTrainDTO dto);

    /**
     * 获取已抵达的车次
     *
     * @param dto
     * @return
     */
    ListQueryVO<ArrivedTrainVO> getArrivedTrainList(GetTrainListDTO dto);
}
