package soft.astanaitbot.complaintService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soft.astanaitbot.complaintService.model.Complaint;
import soft.astanaitbot.complaintService.model.ComplaintType;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>  {
    List<Complaint> findAllByComplaintType(String complaintType);

}
