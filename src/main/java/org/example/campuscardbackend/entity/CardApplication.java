package org.example.campuscardbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card_application")
public class CardApplication {

    @Id
    @Column(name = "app_id", length = 36)
    private String appId;

    @Column(name = "student_id", length = 20, nullable = false)
    private String studentId;

    @Column(name = "apply_time", nullable = false)
    private LocalDateTime applyTime;

    @Column(name = "status", nullable = false)
    private String status; // 可枚举：pending / approved / rejected

    @Column(name = "approved_by", length = 20, nullable = true)
    private String approvedBy;

    @Column(name = "approved_time", nullable = true)
    private LocalDateTime approvedTime;

    // 👇 构造函数（可选）
    public CardApplication() {}

    // 👇 Getter 和 Setter
    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDateTime getApplyTime() { return applyTime; }
    public void setApplyTime(LocalDateTime applyTime) { this.applyTime = applyTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public LocalDateTime getApprovedTime() { return approvedTime; }
    public void setApprovedTime(LocalDateTime approvedTime) { this.approvedTime = approvedTime; }

    // 👇 toString 方法（调试用）
    @Override
    public String toString() {
        return "CardApplication{" +
                "appId='" + appId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", applyTime=" + applyTime +
                ", status='" + status + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedTime=" + approvedTime +
                '}';
    }
}