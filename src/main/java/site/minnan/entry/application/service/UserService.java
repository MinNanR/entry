package site.minnan.entry.application.service;

import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;

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
}
