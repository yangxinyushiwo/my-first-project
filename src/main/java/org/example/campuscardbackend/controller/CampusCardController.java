package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.entity.CampusCard;
import org.example.campuscardbackend.repository.CampusCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
@CrossOrigin(origins = "http://localhost:5173") // 允许 Vite 前端跨域访问
public class CampusCardController {

    @Autowired
    private CampusCardRepository campusCardRepository;

    /**
     * 根据学号获取校园卡信息（余额、状态等）
     *
     * GET /api/card/{studentId}
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getCardByStudentId(@PathVariable String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("学号不能为空");
        }

        Optional<CampusCard> cardOpt = campusCardRepository.findByStudentId(studentId);
        if (cardOpt.isPresent()) {
            return ResponseEntity.ok(cardOpt.get());
        } else {
            return ResponseEntity.status(404).body("未找到该学生的校园卡");
        }
    }
}