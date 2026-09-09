package pl.visa.labmanager.substanceCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class SubstanceCategoryDtoOut {
    private String name;
    private UUID uuid;
    private String categoriesString;
}
