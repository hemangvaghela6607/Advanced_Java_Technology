package com.example.complaint;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComplaintController {

    private final List<Complaint> complaints = new ArrayList<>();
    private int nextId = 1;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("complaints", complaints);
        model.addAttribute("pendingCount", countPending(complaints));
        model.addAttribute("pageTitle", "All Complaints");
        return "index";
    }

    @GetMapping("/pending")
    public String pending(Model model) {
        List<Complaint> pendingComplaints = complaints.stream()
                .filter(c -> "Pending".equals(c.getStatus()))
                .toList();
        model.addAttribute("complaints", pendingComplaints);
        model.addAttribute("pendingCount", countPending(complaints));
        model.addAttribute("pageTitle", "Pending Complaints");
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam String priority) {
        if (title == null || title.isBlank()
                || description == null || description.isBlank()
                || !isValidPriority(priority)) {
            return "redirect:/";
        }
        complaints.add(new Complaint(nextId++, title.trim(),
                description.trim(), priority));
        return "redirect:/";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable int id,
                               @RequestParam String status) {
        if (!isValidStatus(status)) {
            return "redirect:/";
        }
        complaints.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .ifPresent(c -> c.setStatus(status));
        return "redirect:/";
    }

    @PostMapping("/update-priority/{id}")
    public String updatePriority(@PathVariable int id,
                                 @RequestParam String priority) {
        if (!isValidPriority(priority)) {
            return "redirect:/";
        }
        complaints.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .ifPresent(c -> c.setPriority(priority));
        return "redirect:/";
    }

    private long countPending(List<Complaint> list) {
        return list.stream().filter(c -> "Pending".equals(c.getStatus())).count();
    }

    private boolean isValidStatus(String status) {
        return "Pending".equals(status)
                || "In Progress".equals(status)
                || "Resolved".equals(status);
    }

    private boolean isValidPriority(String priority) {
        return "Low".equals(priority)
                || "Medium".equals(priority)
                || "High".equals(priority);
    }
}
