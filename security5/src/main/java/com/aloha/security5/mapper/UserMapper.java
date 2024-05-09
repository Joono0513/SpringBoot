package com.aloha.security5.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.security5.dto.UserAuth;
import com.aloha.security5.dto.Users;

@Mapper
public interface UserMapper {

    // 🔐 사용자 인증(로그인) - id
    public Users login(String username);

    // ✅ 회원 가입
    public int join(Users user) throws Exception;

    // 👩🏻‍💼 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

}