package soft.astanaitbot.complaintService.service;

import soft.astanaitbot.complaintService.model.Complaint;
import soft.astanaitbot.complaintService.model.ComplaintType;

import java.util.List;

public interface ComplaintService {

    List<Complaint> findAll();
    List<Complaint> findAllByComplaintType(String type);
}
