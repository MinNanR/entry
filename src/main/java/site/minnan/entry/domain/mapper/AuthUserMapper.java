package site.minnan.entry.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.vo.user.UserVO;

import java.util.List;

/**
 *
 * @author Minnan on 2021/3/17
 */
@Mapper
@Repository
public interface AuthUserMapper extends BaseMapper<AuthUser> {

    @Select("select id from auth_user where username = #{username}")
    Integer getUserIdByUsername(@Param("username")String username);
}
