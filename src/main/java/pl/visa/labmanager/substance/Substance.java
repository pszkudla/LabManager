package pl.visa.labmanager.substance;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openscience.cdk.interfaces.IAtomContainer;
import pl.visa.labmanager.LabManagerApplication;
import pl.visa.labmanager.safetyDataSheet.SafetyDataSheet;
import pl.visa.labmanager.chemistryUtils.ChemistryUtils;
import pl.visa.labmanager.substanceCategories.SubstanceCategory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "substances", uniqueConstraints = {@UniqueConstraint(columnNames = {"iupac_name", "cas_number"})})
public class Substance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 2048, message = "Nazwa IUPAC powinna mieć od 2 do 2048 znaków.")
    private String iupacName;
    private String casNumber;
    @Size(max = 2024, message = "SMILES nie powinien mieć więcej niż 2048 znaków.")
    private String smiles;


    @Size(min=2, max=2048, message = "InChI powinno mieć od 2 do 2048 znaków.")
    private String inchi;

    @Size(min=27, max=27, message = "Klucz InChI powinien mieć dokładnie 27 znaków.")
    private String inchiKey;

    private String uuid;

    @ManyToMany(mappedBy = "substancesInCategory")
    Set<SubstanceCategory> categoriesList;



    @OneToMany
    @JoinColumn(name = "substance_id")
    private List<SafetyDataSheet> sdsList;

    @ElementCollection
    @CollectionTable(
            name="alt_substance_names",
            joinColumns = @JoinColumn(name="substance_id")
    )
    private Set<AlternativeSubstanceName> alternativeNames;

    @Transient
    private IAtomContainer molecule;

    public Optional<IAtomContainer> getMolecule() {
        if (smiles == null || smiles.isBlank()) {
            return Optional.empty();
        }
        if (molecule == null) {
            molecule = ChemistryUtils.parseSmilesToAtomContainer(smiles);
        }
        return Optional.of(molecule);
    }

    public void addAlternativeName(AlternativeSubstanceName altName) {
        System.out.println("Dodaję alternatywną nazwę: %s".formatted(altName));
        this.getAlternativeNames().add(altName);
    }

    @JsonProperty
    public String photoDir() {
        Path photoPath = Paths.get(LabManagerApplication.dotenv.get("photosPath"), uuid + ".png");
        if (Files.exists(photoPath)) {
            return photoPath.toString();
        }
        else {
            return null;
        }
    }



    public SubstanceDtoOut getDtoOutFromSubstance() {
        SubstanceDtoOut returnedDto = new SubstanceDtoOut();
        returnedDto.setCasNumber(this.getCasNumber());
        returnedDto.setIupacName(this.getIupacName());
        returnedDto.setSmiles(this.getSmiles());
        returnedDto.setUuid(this.getUuid());
        returnedDto.setAlternativeNames(this.getAlternativeNames());
        returnedDto.setSdsList(this.sdsList);
        return returnedDto;
    }
}
