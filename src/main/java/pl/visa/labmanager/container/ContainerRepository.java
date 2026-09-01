package pl.visa.labmanager.container;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.visa.labmanager.substance.Substance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContainerRepository extends JpaRepository<Container, Long> {

    @Query("select c from Container c where c.substance = ?1")
    public List<Container> getContainersBySubstance(Substance substance);

    public Optional<Container> findContainerByUuid(UUID uuid);


}
