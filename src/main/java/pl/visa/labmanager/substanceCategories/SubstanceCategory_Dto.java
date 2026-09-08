package pl.visa.labmanager.substanceCategories;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubstanceCategory_Dto {
    @NotEmpty(message = "Name of the category should not be empty.")
    String name;
}
