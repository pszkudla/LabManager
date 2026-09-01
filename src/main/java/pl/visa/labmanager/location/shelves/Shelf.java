package pl.visa.labmanager.location.shelves;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.visa.labmanager.location.cabinet.Cabinet;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="shelves")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shelfName;
    @ManyToOne
    @JoinColumn(name="cabinet_id")
    private Cabinet cabinet;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    private String getCabinetString() {
        return "%s - %s".formatted(this.getCabinet().getCabinetString(), this.getShelfName());
    }

    public ShelfDTO getShelfDTO() {
        ShelfDTO returnedDto = new ShelfDTO();
        returnedDto.setShelfName(this.getShelfName());
        returnedDto.setUuid(this.getUuid().toString());
        returnedDto.setShelfName(this.getCabinetString());
        return returnedDto;
    }
}
