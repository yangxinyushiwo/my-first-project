package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.dto.RechargeRequest;
import org.example.campuscardbackend.entity.CampusCard;
import org.example.campuscardbackend.entity.TransactionRecord;
import org.example.campuscardbackend.repository.CampusCardRepository;
import org.example.campuscardbackend.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "http://localhost:5173") // ← 关键！解决跨域
public class TransactionController {

    @Autowired
    private CampusCardRepository campusCardRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @PostMapping("/recharge")
    @Transactional // ← 保证数据一致性
    public ResponseEntity<String> recharge(@RequestBody RechargeRequest request) {
        String studentId = request.getStudentId();
        BigDecimal amount = request.getAmount();

        if (studentId == null || studentId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("学号不能为空");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("充值金额必须大于0");
        }

        Optional<CampusCard> cardOpt = campusCardRepository.findByStudentId(studentId);
        if (cardOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("未找到该学生的校园卡，请联系管理员");
        }

        CampusCard card = cardOpt.get();
        if ("lost".equals(card.getStatus())) {
            return ResponseEntity.badRequest().body("校园卡已挂失，无法充值。请先申请补卡审核通过后操作。");
        }

        TransactionRecord record = new TransactionRecord();
        record.setTransId(UUID.randomUUID().toString());
        record.setCardId(card.getCardId());
        record.setType("recharge");
        record.setAmount(amount);
        record.setBalanceBefore(card.getBalance());
        record.setDescription("学生自助充值");
        record.setCreatedAt(LocalDateTime.now());

        transactionRecordRepository.save(record);

        card.setBalance(card.getBalance().add(amount));
        card.setUpdatedAt(LocalDateTime.now());
        campusCardRepository.save(card);

        return ResponseEntity.ok("充值成功！新余额：" + card.getBalance() + " 元");
    }
    // TransactionController.java

    @GetMapping("/records/{studentId}")
    public ResponseEntity<?> getTransactionRecords(@PathVariable String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("学号不能为空");
        }

        // 先查卡
        Optional<CampusCard> cardOpt = campusCardRepository.findByStudentId(studentId);
        if (cardOpt.isEmpty()) {
            return ResponseEntity.status(404).body("未找到该学生的校园卡");
        }

        String cardId = cardOpt.get().getCardId();

        // 再查该卡的所有交易记录（按时间倒序）
        List<TransactionRecord> records = transactionRecordRepository
                .findByCardIdOrderByCreatedAtDesc(cardId);

        return ResponseEntity.ok(records);
    }
}