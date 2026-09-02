package pl.visa.labmanager.substance;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AlternativeSubstanceName {

    @Size(min=2, max=2048)
    @Column(nullable = false)
    private String name;

    @Size(min=1, max=6)
    private String language;

    private UUID uuid = UUID.randomUUID();


}
