package pl.visa.labmanager.location.cabinet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.visa.labmanager.location.lab.Laboratory;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="cabinets")
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cabiinetName;
    @ManyToOne
    @JoinColumn(name="lab_id")
    private Laboratory laboratory;


    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @JsonIgnore
    public String getCabinetString() {
        return this.laboratory.getLaboratoryName() + " - " + this.cabiinetName;
    }


}
