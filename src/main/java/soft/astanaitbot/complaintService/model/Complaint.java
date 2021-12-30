package soft.astanaitbot.complaintService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soft.astanaitbot.userService.model.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String complaint;

    @Column(nullable = false)
    private String complaintType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Complaint(String complaint, String complaintType, User user) {
        this.complaint = complaint;
        this.complaintType = complaintType;
        this.user = user;
    }
}
