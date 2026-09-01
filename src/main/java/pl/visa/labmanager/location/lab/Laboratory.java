package pl.visa.labmanager.location.lab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="laboratories")
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String laboratoryName;
    private String roomNumber;



    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    public String getLaboratoryString() {
        return "%s (%s)".formatted(this.laboratoryName, this.roomNumber);
    }

    @JsonIgnore
    public LaboratoryDTO getLabDTO() {
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
        laboratoryDTO.setLaboratoryName(this.laboratoryName);
        laboratoryDTO.setUuid(this.getUuid().toString());
        laboratoryDTO.setRoomNumber(this.roomNumber);
        laboratoryDTO.setLaboratoryString(this.getLaboratoryString());
        return laboratoryDTO;
    }


}
