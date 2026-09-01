package pl.visa.labmanager.location.shelves;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.location.cabinet.Cabinet;
import pl.visa.labmanager.location.cabinet.CabinetDTO;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ShelfDTO {
    private String shelfName;
    private String uuid;
    private String shelfString;
}
