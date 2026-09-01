package pl.visa.labmanager.location.lab;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class LaboratoryDTO {
    private String laboratoryName;
    private String uuid;
}
