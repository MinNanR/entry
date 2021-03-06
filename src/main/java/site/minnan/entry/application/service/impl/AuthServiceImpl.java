package site.minnan.entry.application.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.application.service.AuthService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.AuthUserMapper;
import site.minnan.entry.domain.vo.auth.LoginVO;
import site.minnan.entry.infrastructure.utils.JwtUtil;
import site.minnan.entry.infrastructure.utils.RedisUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Optional;

@Service("userService")
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private StaffProviderService staffProviderService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String authenticationHeader;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> userOptional = getAuthUser(username);
        AuthUser authUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("??????????????????"));
        return JwtUser.of(authUser);
    }

    /**
     * ????????????token
     *
     * @param authentication
     * @return
     */
    @Override
    public LoginVO generateLoginVO(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = "Bearer " + jwtUtil.generateToken(jwtUser);
        String role =
                jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");
        LoginVO vo = new LoginVO(token, role);
        vo.setRealName(jwtUser.getRealName());
        return vo;
    }

    private Optional<AuthUser> getAuthUser(String username) {
        if (StrUtil.isBlank(username)) return Optional.empty();
        AuthUser userInRedis = (AuthUser) redisUtil.getValue("authUser::" + username);
        if (userInRedis != null) {
            return Optional.of(userInRedis);
        }
        QueryWrapper<AuthUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        AuthUser userInDB = authUserMapper.selectOne(wrapper);
        Optional<AuthUser> userOptional = Optional.ofNullable(userInDB);
        userOptional.ifPresent(user -> redisUtil.setValue("authUser::" + username, user, Duration.ofMinutes(30)));
        return userOptional;
    }

    /**
     * ?????????????????????session
     *
     * @param authentication
     * @param request
     * @param response
     */
    @Override
    public void setToken(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(jwtUser);
        Cookie cookie = new Cookie(authenticationHeader, token);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
