package pl.visa.labmanager.location;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="shelves")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shelfName;
    @ManyToOne
    @JoinColumn(name="cabinet_id")
    private Cabinet cabinet;

}
