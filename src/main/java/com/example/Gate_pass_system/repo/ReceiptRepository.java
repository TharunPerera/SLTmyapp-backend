package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByRequest_RefNo(Long requestId);
    List<Receipt> findByReceivedBy_ServiceNumber(String serviceNumber);
}
