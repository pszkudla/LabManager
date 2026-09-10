package pl.visa.labmanager.safetyDataSheet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import pl.visa.labmanager.substance.Substance;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="safety_data_sheets")
public class SafetyDataSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull(message = "Aby dodać SDS, należy określić substancję jakiej on dotyczy!")
    @ManyToOne
    @JoinColumn(name="substance_id")
    private Substance substance;

    private String originalSourceLink;

    @NotNull(message = "Aby dodać SDS naelży określić jego dostawcę!")
    private String supplier;

    @NotNull(message = "Aby dodać SDS, trzeba określić jego język.")
    private String language;

    private String originalFileName;

    @CreationTimestamp
    private LocalDateTime sdsAddingDateTime;

    private UUID uuid;

    public SDS_DTO getDto() {
        SDS_DTO dto = new SDS_DTO();
        dto.setLanguage(dto.getLanguage());
        dto.setOriginalFileName(dto.getOriginalFileName());
        dto.setOriginalSourceLink(dto.getOriginalSourceLink());
        dto.setUuid(dto.getUuid());
        dto.setSdsAddingDateTime(dto.getSdsAddingDateTime());
        return dto;
    }

}
