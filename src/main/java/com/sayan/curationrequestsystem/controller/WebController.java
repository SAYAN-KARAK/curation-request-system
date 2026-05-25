package com.sayan.curationrequestsystem.controller;

import com.sayan.curationrequestsystem.model.CurationRequest;
import com.sayan.curationrequestsystem.repository.CurationRequestRepository;
import com.sayan.curationrequestsystem.service.CurationRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final CurationRequestService service;

    public WebController(
            CurationRequestService service
    ) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<CurationRequest> requests = service.getAllRequests();

        model.addAttribute("requests", requests);

        model.addAttribute("totalRequests", requests.size());

        model.addAttribute(
                "completedRequests",
                requests.stream()
                        .filter(r -> "COMPLETED".equals(r.getStatus()))
                        .count()
        );

        model.addAttribute(
                "pendingRequests",
                requests.stream()
                        .filter(r -> "PENDING".equals(r.getStatus()))
                        .count()
        );
        model.addAttribute(
                "inProgressRequests",

                requests.stream()
                        .filter(r ->
                                "IN_PROGRESS"
                                        .equals(r.getStatus()))
                        .count()
        );

        return "index";
    }

    @GetMapping("/add")
    public String addRequestPage(Model model){

        model.addAttribute(
                "request",
                new CurationRequest()
        );

        return "add-request";
    }

    @PostMapping("/save")
    public String saveRequest(CurationRequest request) {
        service.saveRequest(request);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editRequest(
            @PathVariable Long id,
            Model model
    ){

        CurationRequest request =
                service.getRequestById(id);

        model.addAttribute("request", request);

        return "edit-request";
    }

    @GetMapping("/delete/{id}")
    public String deleteRequest(
            @PathVariable Long id
    ){

        service.deleteRequest(id);

        return "redirect:/";
    }

    @GetMapping("/view/{id}")
    public String viewRequest(
            @PathVariable Long id,
            Model model
    ){

        CurationRequest request =
                service.getRequestById(id);

        model.addAttribute("request", request);

        return "view-request";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String keyword,
            Model model
    ){

        List<CurationRequest> requests =
                service.searchRequests(keyword);

        model.addAttribute("requests", requests);

        return "index";
    }

    @GetMapping("/status/{status}")
    public String filterByStatus(
            @PathVariable String status,
            Model model
    ){

        model.addAttribute(
                "requests",
                service.getByStatus(status)
        );

        return "index";
    }
}