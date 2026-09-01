package pl.visa.labmanager.location.lab;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="laboratories")
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String laboratoryName;

    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;


}
