package pl.visa.labmanager.container;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ContainerDtoIn {
    private String substanceUuid;
    private String capacity;
    private String notes;
    private UUID zoneUuid;
    private UUID uuid;
}
