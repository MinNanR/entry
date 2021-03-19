import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.entry.EntryApplication;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = EntryApplication.class)
public class TestAddUser {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        AddUserDTO dto = new AddUserDTO();
        dto.setUsername("min");
        dto.setRealName("民难");
        dto.setRole("ADMIN");
        userService.addUser(dto);
    }

    public static void main(String[] args) {
        List<AuthUser> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            AuthUser authUser = new AuthUser(RandomUtil.randomInt(0, 100));
            list.add(authUser);
        }

        Map<Integer, List<AuthUser>> groupById = list.stream()
                .collect(Collectors.groupingBy(AuthUser::getId));
//
//        Set<Integer> keys = groupById.keySet();
//        List<Integer> sorted = CollectionUtil.sort(keys, Comparator.comparingInt(e -> e));
//        sorted = CollectionUtil.reverse(sorted);
//        long start1 = System.currentTimeMillis();
//        int rank = 1;
//        for (Integer key : sorted) {
//            List<AuthUser> sameIds = groupById.get(key);
////            Console.log("第{}名：{}", rank, key);
//            for (AuthUser sameId : sameIds) {
//                sameId.setCreateUser(null, null, null);
//            }
//            rank = rank + sameIds.size();
//        }
//        long end1 = System.currentTimeMillis();
//        Console.log("用时:{}ms", end1 - start1);
//
//
//        List<AuthUser> list1 =
//                list.stream().sorted(Comparator.comparing(AuthUser::getId)).collect(Collectors.toList());
//
//        int index = 0;// 排名
//        double lastScore = -1;// 最近一次的分
//        long start2 = System.currentTimeMillis();
//        for (int i = 0; i < list1.size(); i++) {
//            AuthUser authUser = list1.get(i);
//            Integer s = authUser.getId();
//            if (Double.compare(lastScore, s) != 0) { // 如果成绩和上一名的成绩不相同,那么排名+1
//                lastScore = s;
//                index = i + 1;
//            }
//            authUser.setUpdateUser(null, null, null);
//        }
//        long end2 = System.currentTimeMillis();
//        Console.log("用时:{}ms", end2 - start2);

    }
}
