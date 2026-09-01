package pl.visa.labmanager.location.zone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    public Optional<Zone> findByUuid(UUID uuid);
}
