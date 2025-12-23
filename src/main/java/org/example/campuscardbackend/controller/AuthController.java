package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.entity.UserInfo;
import org.example.campuscardbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserInfo loginRequest) {
        // 1. 查找用户
        UserInfo user = userInfoRepository.findByUserId(loginRequest.getUserId());

        // 2. 验证用户名和密码（明文比对）
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "用户名或密码错误"));
        }

        // 3. 检查账号是否启用
        if (!user.isEnabled()) {
            return ResponseEntity.status(403).body(Map.of("error", "账号已被禁用"));
        }

        // ✅ 4. 登录成功！返回完整用户信息（包括 isFirstLogin）
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("userId", user.getUserId());
        response.put("role", user.getRole());
        response.put("isFirstLogin", user.isFirstLogin()); 

        return ResponseEntity.ok(response);
    }
}
