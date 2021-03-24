package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.DropDownBox;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.location.LocationVO;
import site.minnan.entry.infrastructure.enumerate.LocationType;
import site.minnan.entry.userinterface.dto.location.AddLocationDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationDropDownDTO;
import site.minnan.entry.userinterface.dto.location.GetLocationListDTO;
import site.minnan.entry.userinterface.dto.location.UpdateLocationDTO;

import java.util.List;

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

    /**
     * 获取位置列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<LocationVO> getLocationList(GetLocationListDTO dto);

    /**
     * 更新位置信息
     *
     * @param dto
     */
    void updateLocation(UpdateLocationDTO dto);

    /**
     * 获取位置下拉框
     *
     * @param type
     * @return
     */
    List<DropDownBox> getDropDownBox(GetLocationDropDownDTO type);

    /**
     * 删除位置
     * @param locationId
     */
    void deleteLocation(Integer locationId);
}
