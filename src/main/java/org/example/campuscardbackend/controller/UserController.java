package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.entity.UserInfo;
import org.example.campuscardbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173") // 允许前端跨域
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 获取用户信息（不含密码）
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId) {
        UserInfo user = userInfoRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 创建安全副本（不返回密码）
        UserInfo safeUser = new UserInfo();
        safeUser.setUserId(user.getUserId());
        safeUser.setRole(user.getRole());
        safeUser.setIsFirstLogin(user.isFirstLogin());
        safeUser.setEnabled(user.isEnabled());

        return ResponseEntity.ok(safeUser);
    }

    /**
     * 修改密码（首次登录必须调用）
     */
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String newPassword = payload.get("newPassword");

        // 参数校验
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body("用户ID不能为空");
        }
        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body("新密码至少6位");
        }

        // 查找用户
        UserInfo user = userInfoRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        // 直接写入新密码（不加密）
        user.setPassword(newPassword);
        user.setFirstLogin(false); // ⭐⭐⭐ 关键：修改后不再是首次登录

        // 保存
        userInfoRepository.save(user);

        return ResponseEntity.ok("密码修改成功");
    }
}