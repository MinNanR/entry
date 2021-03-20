package site.minnan.entry.application.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.service.TemperatureService;
import site.minnan.entry.domain.aggregate.TemperatureRecord;
import site.minnan.entry.domain.mapper.TemperatureRecordMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.temperture.TemperatureRecordVO;
import site.minnan.entry.userinterface.dto.temperture.GetTemperatureRecordDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    private TemperatureRecordMapper temperatureRecordMapper;

    /**
     * 获取体温记录列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<TemperatureRecordVO> getTemperatureRecordList(GetTemperatureRecordDTO dto) {
        QueryWrapper<TemperatureRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.format("datediff(create_time, '{}')", dto.getDate()), 0);
        Optional.ofNullable(dto.getHotelId()).ifPresent(s -> queryWrapper.eq("hotel_id", s));
        Optional.ofNullable(dto.getTravelerName()).ifPresent(s -> queryWrapper.eq("traveler_name", s));
        Page<TemperatureRecord> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<TemperatureRecord> page = temperatureRecordMapper.selectPage(queryPage, queryWrapper);
        List<TemperatureRecordVO> list = page.getRecords().stream().map(TemperatureRecordVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }
}
