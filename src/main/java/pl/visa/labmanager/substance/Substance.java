package pl.visa.labmanager.substance;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="substances", uniqueConstraints = {@UniqueConstraint(columnNames = {"iupac_name", "cas_number"})})
public class Substance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2, max=10240)
    private String iupacName;
    private String casNumber;
    private String smiles;
    private String smarts;
}
