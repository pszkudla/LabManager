package pl.visa.labmanager.substance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.safetyDataSheet.SafetyDataSheet;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SubstanceDtoOut {
    private String iupacName;
    private Set<AlternativeSubstanceName> alternativeNames;
    private List<SafetyDataSheet> sdsList;
    private String casNumber;
    private String smiles;
    private String uuid;
}
