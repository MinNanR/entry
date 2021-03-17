package site.minnan.entry.application.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.AuthUserMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Integer check = authUserMapper.getUserIdByUsername(dto.getUsername());
        if (check != null) {
            throw new EntityAlreadyExistException("用户名已存在");
        }
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
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setCreateUser(jwtUser);
        authUserMapper.insert(user);
        if (!Role.ADMIN.equals(user.getRole())) {
            staffProviderService.addStaff(user, dto.getLocationId());
        }
    }

    /**
     * 获取用户列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<UserVO> getUserList(GetUserListDTO dto) {
        QueryWrapper<AuthUser> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(dto.getUsername()).ifPresent(s -> queryWrapper.like("username", s));
        Optional.ofNullable(dto.getRole()).ifPresent(s -> queryWrapper.eq("role", s));
        Page<AuthUser> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<AuthUser> page = authUserMapper.selectPage(queryPage, queryWrapper);
        List<Integer> userIds = page.getRecords().stream()
                .filter(e -> !Role.ADMIN.equals(e.getRole()))
                .map(AuthUser::getId)
                .collect(Collectors.toList());
        Map<Integer, String> staffLocationMap = staffProviderService.getStaffLocationMap(userIds);
        List<UserVO> list = page.getRecords().stream()
                .map(e -> UserVO.assemble(e, staffLocationMap.getOrDefault(e.getId(), "")))
                .collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }
}
