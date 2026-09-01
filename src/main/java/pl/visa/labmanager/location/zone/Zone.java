package pl.visa.labmanager.location.zone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.visa.labmanager.location.shelves.Shelf;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;

    @ManyToOne
    @JoinColumn(name="shelf_id")
    private Shelf shelf;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    public ZoneDTO getDto() {
        ZoneDTO returnedDto = new ZoneDTO();
        returnedDto.setShelfDto(this.getShelf().getShelfDTO());
        returnedDto.setUuid(this.getUuid().toString());
        returnedDto.setZoneName(this.getZoneName());
        return returnedDto;
    }
}
