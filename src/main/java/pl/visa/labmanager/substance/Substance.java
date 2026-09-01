package pl.visa.labmanager.substance;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.visa.labmanager.LabManagerApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Size(min = 2, max = 2048)
    private String iupacName;
    private String casNumber;
    @Size(max = 2024)
    private String smiles;
    private String smarts;
    public String uuid;

    @ElementCollection
    @CollectionTable(
            name="alt_substance_names",
            joinColumns = @JoinColumn(name="substance_id")
    )
    private Set<AlternativeSubstanceName> alternativeNames;

    @JsonProperty
    public String photoDir() {
        Path photoPath = Paths.get(LabManagerApplication.photosPath, uuid + ".png");
        if (Files.exists(photoPath)) {
            return photoPath.toString();
        }
        else {
            return null;
        }
    }



    public SubstanceDTO getDTOFromSubstance() {
        SubstanceDTO returnedDto = new SubstanceDTO();
        returnedDto.setCasNumber(this.getCasNumber());
        returnedDto.setIupacName(this.getIupacName());
        returnedDto.setSmarts(this.getSmarts());
        returnedDto.setSmiles(this.getSmiles());
        returnedDto.setUuid(this.getUuid());
        return returnedDto;
    }
}
