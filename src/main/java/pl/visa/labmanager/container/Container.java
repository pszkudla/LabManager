package pl.visa.labmanager.container;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.visa.labmanager.location.zone.Zone;
import pl.visa.labmanager.substance.Substance;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="containers")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="substance_id")
    private Substance substance;

    private String capacity;

    //Miejsce na uwagi dotyczące opakowania, jeżeli jest nietypowe.
    private String notes;

    @ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @JsonIgnore
    public ContainerDtoOut getDtoOut() {
        ContainerDtoOut returnedDto = new ContainerDtoOut();
        returnedDto.setCapacity(this.getCapacity());
        returnedDto.setNotes(this.getNotes());
        returnedDto.setSubstanceDto(this.getSubstance().getDtoOutFromSubstance());
        returnedDto.setUuid(this.getUuid().toString());
        returnedDto.setZoneDto(this.getZone().getDto());
        return returnedDto;
    }

}
