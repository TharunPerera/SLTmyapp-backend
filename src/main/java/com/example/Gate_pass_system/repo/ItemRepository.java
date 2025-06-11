package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRequest_RefNo(Long requestId);

}
