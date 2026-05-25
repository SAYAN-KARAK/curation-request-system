package com.sayan.curationrequestsystem.controller;

import com.sayan.curationrequestsystem.model.CurationRequest;
import com.sayan.curationrequestsystem.repository.CurationRequestRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class CurationRequestApiController {



    private final CurationRequestRepository repository;

    public CurationRequestApiController(CurationRequestRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CurationRequest> getAllRequests() {

        return repository.findAll();
    }

    @PostMapping
    public CurationRequest createRequest(@RequestBody CurationRequest request) {

        return repository.save(request);
    }

    @GetMapping("/{id}")
    public CurationRequest getRequestById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/{id}")
    public CurationRequest updateRequest(
            @PathVariable Long id,
            @RequestBody CurationRequest updatedRequest
    ) {

        CurationRequest request = repository.findById(id)
                .orElseThrow();

        request.setTitle(updatedRequest.getTitle());
        request.setDescription(updatedRequest.getDescription());
        request.setCategory(updatedRequest.getCategory());
        request.setPriority(updatedRequest.getPriority());
        request.setStatus(updatedRequest.getStatus());
        request.setRequestedBy(updatedRequest.getRequestedBy());

        return repository.save(request);
    }

    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable Long id) {

        repository.deleteById(id);

        return "Request deleted successfully";
    }

}