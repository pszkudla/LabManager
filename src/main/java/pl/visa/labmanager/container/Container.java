package pl.visa.labmanager.container;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Opakowanie musi zawierać substancję!")
    @ManyToOne()
    @JoinColumn(name="substance_id")
    private Substance substance;

    private String capacity;

    //Miejsce na uwagi dotyczące opakowania, jeżeli jest nietypowe.
    private String notes;

    @NotNull(message = "Aby dodać opakowanie, trzeba określić strefę w jakiej się znajduje.")
    @ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    private String supplier;
    private String purity;

    @JsonIgnore
    public ContainerDtoOut getDtoOut() {
        ContainerDtoOut returnedDto = new ContainerDtoOut();
        returnedDto.setCapacity(this.getCapacity());
        returnedDto.setNotes(this.getNotes());
        returnedDto.setSubstanceDto(this.getSubstance().getDtoOutFromSubstance());
        returnedDto.setUuid(this.getUuid().toString());
        returnedDto.setSupplier(this.supplier);
        returnedDto.setZoneDto(this.getZone().getDto());
        return returnedDto;
    }

}
