package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByLocationName(String locationName);
}