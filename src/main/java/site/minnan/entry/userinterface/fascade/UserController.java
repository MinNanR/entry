package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.ListQueryVO;
import site.minnan.entry.domain.vo.user.UserInfoVO;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.userinterface.dto.DetailsQueryDTO;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;
import site.minnan.entry.userinterface.dto.user.UpdatePasswordDTO;
import site.minnan.entry.userinterface.dto.user.UpdateUserDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "用户")
@RestController
@RequestMapping("/entry/user")
public class UserController {


    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDTO dto) {
        return ResponseEntity.success();
    }

    @ApiOperation("查询用户")
    @PostMapping("getUserList")
    public ResponseEntity<ListQueryVO<UserVO>> getUserVO(@RequestBody @Valid GetUserListDTO dto) {
        return ResponseEntity.success(null);
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
    public ResponseEntity<UserInfoVO> getUserInfo(){
        return ResponseEntity.success(null);
    }

    @ApiOperation("禁用用户")
    @PostMapping("disableUser")
    public ResponseEntity<?> disableUser(@RequestBody @Valid DetailsQueryDTO dto){
        return ResponseEntity.success();
    }

    @ApiOperation("启用用户")
    @PostMapping("enableUser")
    public ResponseEntity<?> enableUser(@RequestBody @Valid DetailsQueryDTO dto){
        return ResponseEntity.success();
    }
}
