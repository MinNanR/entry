import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.entry.EntryApplication;
import site.minnan.entry.domain.aggregate.Train;
import site.minnan.entry.domain.mapper.TrainMapper;

import java.util.List;

@SpringBootTest(classes = EntryApplication.class)
public class TestTimeSelect {

    @Autowired
    TrainMapper trainMapper;

    @Test
    public void test() {
        QueryWrapper<Train> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_days(arrive_time)", "to_days(now())");
        List<Train> trains = trainMapper.selectList(queryWrapper);
        Console.log(trains);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.today());
    }
}
