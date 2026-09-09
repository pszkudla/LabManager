package pl.visa.labmanager.substanceCategories;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class SubstanceCategoryDtoIn {
    @NotEmpty(message = "Name of the category should not be empty.")
    String name;
    UUID uuid;
}
