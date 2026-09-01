package pl.visa.labmanager.substance;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubstanceDTO {
    private String iupacName;
    private String casNumber;
    private String smiles;
    private String smarts;
    private String uuid;


}
