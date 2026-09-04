package pl.visa.labmanager.safetyDataSheet;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name="substance_id")
    private Substance substance;

    private String originalSourceLink;

    private String supplier;

    private String language;

    private String originalFileName;

    @CreationTimestamp
    private LocalDateTime sdsAddingDateTime;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

}
