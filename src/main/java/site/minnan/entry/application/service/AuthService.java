package site.minnan.entry.application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.entry.domain.vo.auth.LoginVO;

/**
 * 权限相关服务
 *
 * @author Minnan on 2021/3/11
 */
public interface AuthService extends UserDetailsService {

    /**
     * 生成登录token
     *
     * @param authentication
     * @return
     */
    LoginVO generateLoginVO(Authentication authentication);
}
