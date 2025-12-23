package org.example.campuscardbackend.repository;

import org.example.campuscardbackend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUserId(String userId); // 根据学号/工号查找
}
