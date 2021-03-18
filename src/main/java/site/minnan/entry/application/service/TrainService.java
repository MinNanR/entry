package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.train.TrainVO;
import site.minnan.entry.userinterface.dto.train.AddTrainDTO;
import site.minnan.entry.userinterface.dto.train.GetTrainListDTO;

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
     * 获取车次
     *
     * @param dto
     * @return
     */
    ListQueryVO<TrainVO> getTrainList(GetTrainListDTO dto);
}
