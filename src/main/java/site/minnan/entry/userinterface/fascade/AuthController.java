package site.minnan.entry.userinterface.fascade;

import cn.hutool.crypto.digest.MD5;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.entry.application.service.AuthService;
import site.minnan.entry.domain.vo.auth.LoginVO;
import site.minnan.entry.infrastructure.annocation.OperateLog;
import site.minnan.entry.infrastructure.enumerate.Operation;
import site.minnan.entry.userinterface.dto.auth.PasswordLoginDTO;
import site.minnan.entry.userinterface.response.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "权限")
@Controller
@RequestMapping("/entry/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private AuthService authService;

    @OperateLog(operation = Operation.LOGIN, module = "登录", content = " 登录成功")
    @ApiOperation("密码登录")
    @PostMapping("/login/password")
    @ResponseBody
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

    /**
     * swagger文档登录
     *
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/login/swagger")
    public String swaggerLogin(@Valid PasswordLoginDTO dto, HttpServletRequest request) {
        log.info("swagger登录：{}", new JSONObject(dto));
        Authentication authentication;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
                    MD5.create().digestHex(dto.getPassword())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new DisabledException("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误", e);
        }
        authService.setToken(authentication, request);
        return "redirect:/swagger-ui.html";
    }
}
