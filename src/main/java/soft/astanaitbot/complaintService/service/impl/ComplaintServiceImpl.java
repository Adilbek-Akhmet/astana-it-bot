package soft.astanaitbot.complaintService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.astanaitbot.complaintService.model.Complaint;
import soft.astanaitbot.complaintService.repository.ComplaintRepository;
import soft.astanaitbot.complaintService.service.ComplaintService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    @Transactional
    public List<Complaint> findAllByComplaintType(String type) {
        return complaintRepository.findAllByComplaintType(type);
    }


}
