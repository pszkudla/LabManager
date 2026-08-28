package pl.visa.labmanager.location;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="shelf_zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;

    @ManyToOne
    @JoinColumn(name="shelf_id")
    private Shelf shelf;


}
