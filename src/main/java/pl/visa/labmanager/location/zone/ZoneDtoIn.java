package pl.visa.labmanager.location.zone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ZoneDtoIn {
    private String zoneName;
    private String zoneString;
    private UUID uuid;
    private UUID shelfUuid;
}
