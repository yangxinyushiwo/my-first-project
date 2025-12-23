package org.example.campuscardbackend.repository;

import org.example.campuscardbackend.entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// StudentInfoRepository.java
@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {
    // ID 类型是 String（因为主键是 studentId）
}