package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;
import site.minnan.entry.userinterface.dto.user.UpdatePasswordDTO;
import site.minnan.entry.userinterface.dto.user.UpdateUserDTO;

public interface UserService {

    /**
     * 添加用户
     *
     * @param dto
     */
    void addUser(AddUserDTO dto);

    /**
     * 获取用户列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<UserVO> getUserList(GetUserListDTO dto);

    /**
     * 修改指定用户密码
     *
     * @param dto
     */
    void updatePassword(UpdatePasswordDTO dto);

    /**
     * 更新当前用户信息
     *
     * @param dto
     */
    void updateUserInfo(UpdateUserDTO dto);

    /**
     * 禁用用户
     * @param id 用户id
     */
    void disableUser(Integer id);

    /**
     * 启用用户
     * @param id 用户id
     */
    void enableUser(Integer id);
}
