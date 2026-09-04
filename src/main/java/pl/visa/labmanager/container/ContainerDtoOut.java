package pl.visa.labmanager.container;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.location.zone.ZoneDtoOut;

import pl.visa.labmanager.substance.SubstanceDtoOut;


@NoArgsConstructor
@Getter
@Setter
public class ContainerDtoOut {
    private SubstanceDtoOut substanceDto;
    private String capacity;
    private String notes;
    private ZoneDtoOut zoneDto;
    private String uuid;
}
