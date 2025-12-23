package org.example.campuscardbackend.repository;

import org.example.campuscardbackend.entity.CardApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 补卡申请数据访问接口
 */
@Repository
public interface CardApplicationRepository extends JpaRepository<CardApplication, String> {

    /**
     * 判断指定学号是否存在状态为给定值的申请（用于防止重复提交）
     *
     * @param studentId 学号
     * @param status    申请状态（如 "pending"）
     * @return 是否存在
     */
    boolean existsByStudentIdAndStatus(String studentId, String status);

    /**
     * 根据状态查询所有补卡申请
     *
     * @param status 申请状态（如 "pending", "approved", "rejected"）
     * @return 申请列表
     */
    List<CardApplication> findByStatus(String status);
}