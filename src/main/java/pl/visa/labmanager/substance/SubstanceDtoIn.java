package pl.visa.labmanager.substance;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubstanceDtoIn {
    @Size(min = 2, max = 2048)
    private String iupacName;
    private String casNumber;
    @Size(max = 2024)
    private String smiles;
    private String smarts;
    public String uuid;
}
