package pl.visa.labmanager.substance;

import jakarta.persistence.*;
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

    @Size(min=2, max=2048, message = "Alternatywna nazwa powinna mieć od 2 do 2048 znaków.")
    @Column(nullable = false)
    private String name;

    @Size(min=1, max=6, message = "Oznaczenie języka powinno mieć od 1 do 6 znaków.")
    private String language;

    private UUID uuid = UUID.randomUUID();


}
