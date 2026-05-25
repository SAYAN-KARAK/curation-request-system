package com.sayan.curationrequestsystem.repository;

import com.sayan.curationrequestsystem.model.CurationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurationRequestRepository extends JpaRepository<CurationRequest, Long> {
    List<CurationRequest> findByTitleContainingIgnoreCase(String keyword);
    List<CurationRequest> findByStatus(String status);
    List<CurationRequest>
    findAllByOrderByCreatedAtDesc();
    List<CurationRequest>
    findByPriority(String priority);

    List<CurationRequest>
    findByCategory(String category);
}
