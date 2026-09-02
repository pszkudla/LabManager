package pl.visa.labmanager.location.shelves;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ShelfDtoIn {
    private String shelfName;
    private UUID uuid;
    private UUID cabinetUuid;
}
