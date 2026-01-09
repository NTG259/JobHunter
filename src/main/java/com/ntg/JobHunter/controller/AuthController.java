package com.ntg.JobHunter.controller;

import com.nimbusds.jose.proc.SecurityContext;
import com.ntg.JobHunter.domain.RestResponse;
import com.ntg.JobHunter.domain.dto.LoginDTO;
import com.ntg.JobHunter.util.security.TokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenUtil tokenUtil;


    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenUtil tokenUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        // Nạp input vào luồng Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // Viết hàm loadByUser để xác thực người dùng
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Tạo token
        String access_token = this.tokenUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestResponse<Object> res = new RestResponse<>();
        res.setData(access_token);
        return ResponseEntity.ok().body(res);
    }
}
