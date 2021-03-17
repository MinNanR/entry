package site.minnan.entry.application.service;

import site.minnan.entry.userinterface.dto.location.AddLocationDTO;

/**
 * @author Minnan on 2021/3/17
 */
public interface LocationService {

    /**
     * 添加位置
     *
     * @param dto
     */
    void addLocation(AddLocationDTO dto);
}
