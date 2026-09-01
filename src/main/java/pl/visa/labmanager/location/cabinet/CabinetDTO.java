package pl.visa.labmanager.location.cabinet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pl.visa.labmanager.location.lab.LaboratoryDTO;

@NoArgsConstructor
@Setter
@Getter
public class CabinetDTO {

    private String cabinetName;
    private LaboratoryDTO laboratory;
    private String uuid;
}
