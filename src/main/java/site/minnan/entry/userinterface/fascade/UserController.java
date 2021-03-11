package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.vo.user.UserVO;
import site.minnan.entry.userinterface.dto.user.GetUserListDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "用户")
@RestController
@RequestMapping("/entry/user")
public class UserController {


    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public ResponseEntity<UserVO> getUserVO(@RequestBody @Valid GetUserListDTO dto){

        return ResponseEntity.success(null);
    }
}
