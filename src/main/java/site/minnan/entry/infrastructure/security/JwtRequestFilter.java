package site.minnan.entry.infrastructure.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.infrastructure.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * jwt过滤器（鉴权）
 *
 * @author Minnan on 2020/12/16
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String authenticationHeader;

    @Value("${jwt.route.authentication.path}")
    private String[] authenticationPath;

    @Autowired
    @Qualifier(value = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String requestTokenHeader = getTokenString(request);
            String username = null;
            String jwtToken = null;
            //获取token
            if (requestTokenHeader != null) {
                if (requestTokenHeader.startsWith("Bearer ")) {
                    jwtToken = requestTokenHeader.substring(7);
                    try {
                        username = jwtUtil.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException e) {
                        log.info("获取token信息失败");
                    } catch (ExpiredJwtException e) {
                        log.info("token已过期");
                    }
                    if (username != null) {
                        JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken token =
                                    new UsernamePasswordAuthenticationToken(userDetails,
                                            null, userDetails.getAuthorities());
                            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(token);
                            Date expireDate = jwtUtil.getExpirationDateFromToken(jwtToken);
                            long leftTime = DateUtil.between(expireDate, DateTime.now(), DateUnit.HOUR);
                            if (leftTime < 24L) {
                                JwtUser jwtUser = (JwtUser) token.getPrincipal();
                                String newToken = jwtUtil.generateToken(jwtUser);
                                response.addHeader("newToken", newToken);
                            }
                        }
                    }
                } else {
                    if (!"OPTIONS".equals(request.getMethod())) {
                        log.warn("JWT Token does not begin with bearer String");
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenString(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(authenticationHeader);
        if (StrUtil.isNotBlank(requestTokenHeader)) {
            return requestTokenHeader;
        }
        Cookie[] cookies = request.getCookies();
        if(ArrayUtil.isNotEmpty(cookies)){
            for (Cookie cookie : cookies) {
                if(authenticationHeader.equals(cookie.getName())){
                    return "Bearer " + cookie.getValue();
                }
            }
        }
        return null;
    }
}
