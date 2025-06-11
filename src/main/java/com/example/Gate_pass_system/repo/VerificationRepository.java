package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByRequest_RefNo(Long requestId);
    List<Verification> findByVerifiedBy_ServiceNumber(String serviceNumber);
}