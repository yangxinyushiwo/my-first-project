package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.entity.CardApplication;
import org.example.campuscardbackend.entity.CampusCard;
import org.example.campuscardbackend.repository.CardApplicationRepository;
import org.example.campuscardbackend.repository.CampusCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://localhost:5173")
public class CardApplicationController {

    @Autowired
    private CardApplicationRepository cardApplicationRepository;

    @Autowired
    private CampusCardRepository campusCardRepository;

    // 👇 1. 学生端：提交挂失补卡申请
    @PostMapping("/card/lost")
    public ResponseEntity<String> submitLostCard(@RequestBody Map<String, String> request) {
        String studentId = request.get("studentId");

        if (studentId == null || studentId.isEmpty()) {
            return ResponseEntity.badRequest().body("学号不能为空");
        }

        // 防止重复提交 pending 申请
        if (cardApplicationRepository.existsByStudentIdAndStatus(studentId, "pending")) {
            return ResponseEntity.badRequest().body("您已有待审核的补卡申请，请勿重复提交");
        }

        // 挂失原校园卡（如果存在且未挂失）
        Optional<CampusCard> cardOpt = campusCardRepository.findByStudentId(studentId);
        if (cardOpt.isPresent()) {
            CampusCard card = cardOpt.get();
            if (!"lost".equals(card.getStatus())) {
                card.setStatus("lost");
                card.setUpdatedAt(LocalDateTime.now());
                campusCardRepository.save(card);
            }
        }

        // 创建新的补卡申请
        CardApplication application = new CardApplication();
        application.setAppId(UUID.randomUUID().toString()); // 对应 app_id
        application.setStudentId(studentId);                // 对应 student_id
        application.setApplyTime(LocalDateTime.now());      // 对应 apply_time
        application.setStatus("pending");                   // 对应 status

        // approved_by 和 approved_time 留空（默认 null）

        cardApplicationRepository.save(application);

        return ResponseEntity.ok("补卡申请提交成功，校园卡已挂失，请等待管理员审核");
    }

    // 👇 2. 管理员端：获取所有待审核申请
    @GetMapping("/admin/applications")
    public ResponseEntity<List<CardApplication>> getPendingApplications() {
        List<CardApplication> applications = cardApplicationRepository.findByStatus("pending");
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/admin/applications/{appId}/approve")
    public ResponseEntity<String> approveApplication(@PathVariable String appId) {
        Optional<CardApplication> appOpt = cardApplicationRepository.findById(appId);
        if (appOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CardApplication app = appOpt.get();
        if (!"pending".equals(app.getStatus())) {
            return ResponseEntity.badRequest().body("该申请已处理，无法再次操作");
        }

        // 更新审批信息
        app.setStatus("approved");
        app.setApprovedBy("admin");
        app.setApprovedTime(LocalDateTime.now());
        cardApplicationRepository.save(app);

        // 🔥 关键：查找并恢复原校园卡状态
        Optional<CampusCard> cardOpt = campusCardRepository.findByStudentId(app.getStudentId());
        if (cardOpt.isPresent()) {
            CampusCard card = cardOpt.get();
            if ("lost".equals(card.getStatus())) {
                card.setStatus("active");
                card.setUpdatedAt(LocalDateTime.now());
                campusCardRepository.save(card);
            }
        } else {
            // 如果找不到原卡，提示管理员
            return ResponseEntity.badRequest().body("未找到该学生的校园卡，请检查数据");
        }

        return ResponseEntity.ok("补卡申请已通过，原校园卡状态已恢复为正常");
    }
    // 👇 4. 管理员端：审核拒绝
    @PostMapping("/admin/applications/{appId}/reject")
    public ResponseEntity<String> rejectApplication(@PathVariable String appId) {
        Optional<CardApplication> appOpt = cardApplicationRepository.findById(appId);
        if (appOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CardApplication app = appOpt.get();
        if (!"pending".equals(app.getStatus())) {
            return ResponseEntity.badRequest().body("该申请已处理，无法再次操作");
        }

        // 记录拒绝操作
        app.setStatus("rejected");
        app.setApprovedBy("admin"); // TODO: 替换为真实管理员工号
        app.setApprovedTime(LocalDateTime.now());

        cardApplicationRepository.save(app);

        return ResponseEntity.ok("补卡申请已拒绝");
    }
}