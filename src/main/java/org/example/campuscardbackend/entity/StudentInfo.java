package org.example.campuscardbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_info")
public class StudentInfo {
    @Id
    private String studentId;

    private String name;
    private String idCard;
    private String phone;
    private String email;
    private String department;
    private boolean infoComplete;

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public boolean isInfoComplete() { return infoComplete; }
    public void setInfoComplete(boolean infoComplete) { this.infoComplete = infoComplete; }
}