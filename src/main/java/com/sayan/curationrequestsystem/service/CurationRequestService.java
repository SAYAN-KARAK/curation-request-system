package com.sayan.curationrequestsystem.service;

import com.sayan.curationrequestsystem.model.CurationRequest;
import com.sayan.curationrequestsystem.repository.CurationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurationRequestService {

    private final CurationRequestRepository repository;

    public CurationRequestService(
            CurationRequestRepository repository
    ) {
        this.repository = repository;
    }

    public List<CurationRequest> getAllRequests(){

        return repository.findAllByOrderByCreatedAtDesc();
    }

    public CurationRequest saveRequest(
            CurationRequest request
    ){

        applySmartFeatures(request);

        return repository.save(request);
    }

    public void deleteRequest(Long id){

        repository.deleteById(id);
    }

    public CurationRequest getRequestById(Long id){

        return repository.findById(id)
                .orElseThrow();
    }

    public List<CurationRequest> searchRequests(
            String keyword
    ){

        return repository
                .findByTitleContainingIgnoreCase(keyword);
    }

    public List<CurationRequest> getByStatus(
            String status
    ){

        return repository.findByStatus(status);
    }

    private void applySmartFeatures(
            CurationRequest request
    ){

        if (request.getTitle() == null) return;

        String title = request.getTitle().toLowerCase();

        // Smart Category Suggestion

        if(title.contains("machine learning")
                || title.contains("deep learning")
                || title.contains("artificial intelligence")){

            request.setCategory("AI");
        }

        // Smart Priority Recommendation

        if(title.contains("urgent")
                || title.contains("critical")
                || title.contains("immediately")){

            request.setPriority("HIGH");
        }
    }
}