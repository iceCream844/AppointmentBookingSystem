package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "appointments")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Appointment extends BaseClass {

    private LocalDateTime appointmentTime;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
