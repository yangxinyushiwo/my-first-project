package org.example.campuscardbackend.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    private String userId; // 主键

    private String password;
    private String role;
    private boolean enabled;
    private boolean isFirstLogin;

    // 单向 OneToOne：通过外键关联 student_info 表
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private StudentInfo studentInfo;

    // Getter and Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isFirstLogin() { return isFirstLogin; }
    public void setFirstLogin(boolean firstLogin) { this.isFirstLogin = firstLogin; }

    public StudentInfo getStudentInfo() { return studentInfo; }
    public void setStudentInfo(StudentInfo studentInfo) { this.studentInfo = studentInfo; }

    public void setIsFirstLogin(Boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }
}