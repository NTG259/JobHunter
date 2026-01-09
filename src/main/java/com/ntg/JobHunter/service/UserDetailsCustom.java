package com.ntg.JobHunter.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


@Component("userDetailService")
public class UserDetailsCustom implements UserDetailsService {
    private final UserService userService;

    public UserDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    // Class này KHÔNG xác thực mật khẩu, KHÔNG so sánh password, KHÔNG sinh token.
    // Với username này, user là ai? có password gì? có quyền gì?
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Nếu sai email -> chưa xử lý
        // Nếu sai mật khẩu -> chưa xử lý
        com.ntg.JobHunter.domain.User user = userService.handleGetUserByEmail(username);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
