package pl.visa.labmanager.container;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ContainerDtoForSubstanceModal {
    private String capacity;

    private String containerNotes;
    private String zoneString;
    private UUID containerUuid;

}
