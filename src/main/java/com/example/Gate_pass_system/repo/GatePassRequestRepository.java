package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.GatePassRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatePassRequestRepository extends JpaRepository<GatePassRequest, Long> {
    List<GatePassRequest> findByExecutiveOfficerServiceNumberAndStatus(String executiveOfficerServiceNumber, String status);

    Optional<GatePassRequest> findByRefNo(Long refNo);


    // New methods for duty officer
    // Find requests that are approved by executive officer but not yet verified by any duty officer
    @Query("SELECT gpr FROM GatePassRequest gpr WHERE gpr.status = 'Approved' AND " +
            "NOT EXISTS (SELECT v FROM Verification v WHERE v.request = gpr)")
    List<GatePassRequest> findApprovedRequestsNotVerified();

    List<GatePassRequest> findByStatus(String status);

    List<GatePassRequest> findBySenderServiceNumber(String serviceNumber);

    //newlu added
List<GatePassRequest> findByExecutiveOfficer_ServiceNumberAndStatus(String serviceNumber, String status);

    // Add the method that's causing the error
    List<GatePassRequest> findByStatusAndReceiver_ServiceNumber(String status, String receiverServiceNumber);

}
