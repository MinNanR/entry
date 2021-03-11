package site.minnan.entry.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.minnan.entry.domain.aggregate.AuthUser;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * JWT实体类
 *
 * @author Minnan on 2020/12/16
 */
@Data
@Builder
@AllArgsConstructor
public class JwtUser implements UserDetails {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户登陆名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 认证是否过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码戳
     */
    private String passwordStamp;


    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public JwtUser() {
    }

    public static JwtUser of(AuthUser authUser) {
        String roleName = authUser.getRole().getValue();
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));
        return JwtUser.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .password(authUser.getPassword())
                .authorities(grantedAuthorities)
                .enabled(authUser.getEnabled().equals(1))
                .passwordStamp(authUser.getPasswordStamp())
                .build();
    }
}
