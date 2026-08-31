package pl.visa.labmanager.container;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.visa.labmanager.location.Zone;
import pl.visa.labmanager.substance.Substance;

@Entity
@Getter
@Setter
@Table(name="containers")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="substance_id")
    private Substance substance;

    private String capacity;

    //Miejsce na uwagi dotyczące opakowania, jeżeli jest nietypowe.
    private String notes;

    @ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;

}
