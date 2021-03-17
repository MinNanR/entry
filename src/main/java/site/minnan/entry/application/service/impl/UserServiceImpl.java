package site.minnan.entry.application.service.impl;

import cn.hutool.crypto.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.mapper.AuthUserMapper;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Value("${jwt.initiatedPassword}")
    private String initiatedPassword;

    @Autowired
    private StaffProviderService staffProviderService;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * 添加用户
     *
     * @param dto
     */
    @Override
    public void addUser(AddUserDTO dto) {
        String md5Password = MD5.create().digestHex(initiatedPassword);
        String encodedPassword = encoder.encode(md5Password);
        String stamp = UUID.randomUUID().toString().replaceAll("-", "");
        AuthUser user = AuthUser.builder()
                .username(dto.getUsername())
                .realName(dto.getRealName())
                .role(Role.valueOf(dto.getRole()))
                .password(encodedPassword)
                .passwordStamp(stamp)
                .enabled(AuthUser.ENABLE)
                .build();
        authUserMapper.insert(user);
        if (!Role.ADMIN.equals(user.getRole())){
            staffProviderService.addStaff(user, dto.getLocationId());
        }
    }
}
