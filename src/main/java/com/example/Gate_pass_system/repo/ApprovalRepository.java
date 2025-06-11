package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    // Change this to return Optional
    Optional<Approval> findByRequest_RefNo(Long refNo);

    // Keep other methods
    List<Approval> findByApprovedBy_ServiceNumber(String serviceNumber);
}