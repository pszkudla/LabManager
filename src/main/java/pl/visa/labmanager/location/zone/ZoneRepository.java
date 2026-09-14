package pl.visa.labmanager.location.zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.visa.labmanager.location.shelves.Shelf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    public Optional<Zone> findByUuid(UUID uuid);

    @Query("select z from Zone z where z.shelf = ?1")
    public List<Zone> getAllZonesFromShelf(Shelf shelf);
}
