package pl.visa.labmanager.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    public String getCabinetString() {
        return this.laboratory.getLaboratoryName() + " - " + this.cabiinetName;
    }


}
