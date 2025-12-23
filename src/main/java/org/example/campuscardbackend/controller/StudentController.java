package org.example.campuscardbackend.controller;

import org.example.campuscardbackend.entity.StudentInfo;
import org.example.campuscardbackend.repository.StudentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @GetMapping("/{studentId}")
    public StudentInfo getStudentInfo(@PathVariable String studentId) {
        return studentInfoRepository.findById(studentId).orElse(null);
    }
    @GetMapping("/all")
    public List<StudentInfo> getAllStudents() {
        return studentInfoRepository.findAll();
    }
    @PutMapping("/{studentId}")
    public String updateStudentInfo(@PathVariable String studentId, @RequestBody StudentInfo updatedInfo) {
        if (!studentInfoRepository.existsById(studentId)) {
            return "Student not found";
        }
        updatedInfo.setStudentId(studentId); // 确保 ID 不变
        studentInfoRepository.save(updatedInfo);
        return "Update success";
    }
}