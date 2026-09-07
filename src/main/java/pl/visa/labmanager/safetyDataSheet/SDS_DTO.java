package pl.visa.labmanager.safetyDataSheet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SDS_DTO {
    private String substanceUuid;
    private String language;
    private String supplier;
    private String originalSourceLink;
    private String originalFileName;
    private UUID uuid;
}
