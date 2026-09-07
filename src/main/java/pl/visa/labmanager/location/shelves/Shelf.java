package pl.visa.labmanager.location.shelves;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "Nazwa półki nie może być pusta.")
    private String shelfName;

    @NotNull(message = "Półka musi znajdować się w szafce.")
    @ManyToOne
    @JoinColumn(name="cabinet_id")
    private Cabinet cabinet;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;


    public String getShelfString() {
        return "%s - %s".formatted(this.getCabinet().getCabinetString(), this.getShelfName());
    }

    public ShelfDtoOut getShelfDTO() {
        ShelfDtoOut returnedDto = new ShelfDtoOut();
        returnedDto.setShelfName(this.getShelfName());
        returnedDto.setUuid(this.getUuid().toString());
        returnedDto.setShelfString(this.getShelfString());
        return returnedDto;
    }
}
