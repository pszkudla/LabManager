package pl.visa.labmanager.substanceCategories;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.substance.Substance;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name="chemicals_categories")
public class SubstanceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "Nazwa kategorii substancji nie może być pusta!")
    private String name;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="substance_category", joinColumns = @JoinColumn(name="category_id"), inverseJoinColumns = @JoinColumn(name="substance_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id", "substance_id"})})
    private Set<Substance> substancesInCategory;


    public SubstanceCategoryDtoOut getDto() {
        SubstanceCategoryDtoOut dto = new SubstanceCategoryDtoOut();
        dto.setName(this.getName());
        dto.setUuid(this.uuid);
        String substancesString = substancesInCategory.stream().map(Substance::getIupacName).collect(Collectors.joining(", "));
        dto.setCategoriesString(substancesString);
        return dto;
    }

}
