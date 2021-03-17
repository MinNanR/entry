package site.minnan.entry.application.service;

import site.minnan.entry.userinterface.dto.user.AddUserDTO;

public interface UserService {

    /**
     * 添加用户
     *
     * @param dto
     */
    void addUser(AddUserDTO dto);
}
