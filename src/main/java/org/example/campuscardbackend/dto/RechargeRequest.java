package org.example.campuscardbackend.dto;

import java.math.BigDecimal;

public class RechargeRequest {
    private String studentId;
    private BigDecimal amount;

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}