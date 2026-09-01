package pl.visa.labmanager.container;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.location.zone.ZoneDTO;

import pl.visa.labmanager.substance.SubstanceDTO;


@NoArgsConstructor
@Getter
@Setter
public class ContainerDTO {
    private SubstanceDTO substanceDto;
    private String capacity;
    private String notes;
    private ZoneDTO zoneDto;
    private String uuid;
}
