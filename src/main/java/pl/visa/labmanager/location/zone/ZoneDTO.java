package pl.visa.labmanager.location.zone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.location.shelves.Shelf;
import pl.visa.labmanager.location.shelves.ShelfDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ZoneDTO {
    private String zoneName;
    private ShelfDTO shelfDto;
    private String uuid;
}
