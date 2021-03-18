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
import org.springframework.transaction.annotation.Transactional;
import site.minnan.entry.application.provider.StaffProviderService;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.domain.aggregate.AuthUser;
import site.minnan.entry.domain.entity.JwtUser;
import site.minnan.entry.domain.mapper.AuthUserMapper;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.infrastructure.enumerate.Role;
import site.minnan.entry.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.entry.infrastructure.exception.EntityNotExistException;
import site.minnan.entry.infrastructure.utils.RedisUtil;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;
import site.minnan.entry.userinterface.dto.user.UpdatePasswordDTO;
import site.minnan.entry.userinterface.dto.user.UpdateUserDTO;

import java.util.List;
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

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加用户
     *
     * @param dto
     */
    @Override
    @Transactional()
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
            //创建的用户不是管理员时同时创建工作人员
            staffProviderService.addStaff(user);
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
        queryWrapper.orderByDesc("update_time");
        Page<AuthUser> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<AuthUser> page = authUserMapper.selectPage(queryPage, queryWrapper);
        List<UserVO> list = page.getRecords().stream()
                .map(UserVO::assemble)
                .collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 修改指定用户密码
     *
     * @param dto
     */
    @Override
    public void updatePassword(UpdatePasswordDTO dto) {
        AuthUser authUser = authUserMapper.selectById(dto.getId());
        if (authUser == null) {
            throw new EntityNotExistException("用户不存在");
        }
        String encodedPassword = encoder.encode(dto.getNewPassword());
        String stamp = UUID.randomUUID().toString().replaceAll("-", "");
        authUser.updatePassword(encodedPassword, stamp);
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authUser.setUpdateUser(jwtUser);
        redisUtil.delete("authUser::" + authUser.getUsername());
        authUserMapper.updateById(authUser);
    }

    /**
     * 更新用户信息
     *
     * @param dto
     */
    @Override
    public void updateUserInfo(UpdateUserDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser authUser = authUserMapper.selectById(jwtUser.getId());
        Optional.ofNullable(dto.getNewPassword()).ifPresent(s -> {
            String encodedPassword = encoder.encode(dto.getNewPassword());
            String stamp = UUID.randomUUID().toString().replaceAll("-", "");
            authUser.updatePassword(encodedPassword, stamp);
        });
        Optional.ofNullable(dto.getRealName()).ifPresent(authUser::setRealName);
        authUser.setUpdateUser(jwtUser);
        redisUtil.delete("authUser::" + authUser.getUsername());
        authUserMapper.updateById(authUser);
    }

    /**
     * 禁用用户
     *
     * @param id 用户id
     */
    @Override
    public void disableUser(Integer id) {
        AuthUser authUser = authUserMapper.selectById(id);
        if (authUser == null) {
            throw new EntityNotExistException("用户不存在存在");
        }
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authUser.disable();
        authUser.setUpdateUser(jwtUser);
        redisUtil.delete("authUser::" + authUser.getUsername());
        authUserMapper.updateById(authUser);
    }

    /**
     * 启用用户
     *
     * @param id 用户id
     */
    @Override
    public void enableUser(Integer id) {
        AuthUser authUser = authUserMapper.selectById(id);
        if (authUser == null) {
            throw new EntityNotExistException("用户不存在存在");
        }
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authUser.enable();
        authUser.setUpdateUser(jwtUser);
        redisUtil.delete("authUser::" + authUser.getUsername());
        authUserMapper.updateById(authUser);
    }
}
