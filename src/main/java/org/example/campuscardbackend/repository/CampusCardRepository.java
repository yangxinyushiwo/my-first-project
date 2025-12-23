package org.example.campuscardbackend.repository;

import org.example.campuscardbackend.entity.CampusCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CampusCardRepository extends JpaRepository<CampusCard, String> {
    Optional<CampusCard> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
}