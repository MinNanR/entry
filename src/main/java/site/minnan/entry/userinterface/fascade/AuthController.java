package site.minnan.entry.userinterface.fascade;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.application.service.AuthService;
import site.minnan.entry.domain.vo.auth.LoginVO;
import site.minnan.entry.userinterface.dto.auth.PasswordLoginDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "权限")
@RestController
@RequestMapping("/entry/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private AuthService authService;

    @ApiOperation("密码登录")
    @PostMapping("/login/password")
    public ResponseEntity<LoginVO> passwordLogin(@RequestBody @Valid PasswordLoginDTO dto) {
        log.info("用户登录，登录信息：{}", new JSONObject(dto));
        Authentication authentication;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
                    dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new DisabledException("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误", e);
        }
        LoginVO vo = authService.generateLoginVO(authentication);
        return ResponseEntity.success(vo);
    }
}
