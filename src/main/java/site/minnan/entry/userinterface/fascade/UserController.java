package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserInfoVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;
import site.minnan.entry.userinterface.dto.user.UpdatePasswordDTO;
import site.minnan.entry.userinterface.dto.user.UpdateUserDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(tags = "用户")
@RestController
@RequestMapping("/entry/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDTO dto) {
        userService.addUser(dto);
        return ResponseEntity.success();
    }

    @ApiOperation("查询用户")
    @PostMapping("getUserList")
    public ResponseEntity<ListQueryVO<UserVO>> getUserVO(@RequestBody @Valid GetUserListDTO dto) {
        ListQueryVO<UserVO> vo = userService.getUserList(dto);
        return ResponseEntity.success(vo);
    }

    @ApiOperation("修改指定用户的密码")
    @PostMapping("updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDTO dto) {
        return ResponseEntity.success();
    }

    @ApiOperation("修改当前用户的信息（密码和真实姓名)")
    @PostMapping("updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid UpdateUserDTO dto) {
        return ResponseEntity.success();
    }

    @ApiOperation("获取当前用户信息")
    @PostMapping("getUserInfo")
    public ResponseEntity<UserInfoVO> getUserInfo() {
        return ResponseEntity.success(null);
    }

    @ApiOperation("禁用用户")
    @PostMapping("disableUser/{id}")
    public ResponseEntity<?> disableUser(@ApiParam(value = "用户id", required = true, example = "1")
                                         @RequestBody @PathVariable("id") @NotNull(message = "未指定禁用的用户") Integer id) {
        return ResponseEntity.success();
    }

    @ApiOperation("启用用户")
    @PostMapping("enableUser/{id}")
    public ResponseEntity<?> enableUser(@ApiParam(value = "用户id", required = true, example = "1")
                                        @Valid @PathVariable("id") @NotNull(message = "未指定启用的用户") Integer id) {
        return ResponseEntity.success();
    }
}
