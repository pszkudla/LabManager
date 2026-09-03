package pl.visa.labmanager.location.cabinet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class CabinetDtoIn {

    private UUID labUuid;
    private UUID uuid;
    private String cabinetName;
}
