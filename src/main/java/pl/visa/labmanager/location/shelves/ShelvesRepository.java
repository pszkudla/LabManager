package pl.visa.labmanager.location.shelves;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.visa.labmanager.location.cabinet.Cabinet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShelvesRepository extends JpaRepository<Shelf, Long> {
    public Optional<Shelf> getShelfByUuid(UUID uuid);

    @Query("select s from Shelf s where s.cabinet = ?1")
    public List<Shelf> getAllShelvesFromCabinet(Cabinet cabinet);
}
