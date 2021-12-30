package soft.astanaitbot.complaintService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.astanaitbot.complaintService.model.Complaint;
import soft.astanaitbot.complaintService.model.ComplaintType;
import soft.astanaitbot.complaintService.repository.ComplaintRepository;
import soft.astanaitbot.complaintService.service.ComplaintService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/all")
    public List<Complaint> findAll() {
        return complaintService.findAll();
    }

    @GetMapping("/to/students")
    public List<Complaint> findAllByComplaintTypeToStudent() {
        return complaintService.findAllByComplaintType(ComplaintType.toSTUDENT.name());
    }

    @GetMapping("/to/teachers")
    public List<Complaint> findAllByComplaintTypeToTeachers() {
        return complaintService.findAllByComplaintType(ComplaintType.toTEACHER.name());
    }

    @GetMapping("/to/admin")
    public List<Complaint> findAllByComplaintToAdmin() {
        return complaintService.findAllByComplaintType(ComplaintType.toADMINISTRATION.name());
    }
}
