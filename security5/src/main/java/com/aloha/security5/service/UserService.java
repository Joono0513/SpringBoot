package com.aloha.security5.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.aloha.security5.dto.UserAuth;
import com.aloha.security5.dto.Users;

@Service
public interface UserService {

     // 🔐 사용자 인증(로그인) - id
    public void login(Users user, HttpServletRequest reuqest);

    // ✅ 회원 가입
    public int join(Users user) throws Exception;

    // 👩🏻‍💼 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

}
