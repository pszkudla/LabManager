package pl.visa.labmanager.container;

import jakarta.persistence.*;

@Entity
@Table(name="container")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
