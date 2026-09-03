package pl.visa.labmanager.safetyDataSheet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SDS_DTO_in {
    private String substanceUuid;
    private String language;
    private String supplier;
    private String originalSourceLink;
}
