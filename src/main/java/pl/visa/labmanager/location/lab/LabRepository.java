package pl.visa.labmanager.location.lab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LabRepository extends JpaRepository<Laboratory, Long> {

    @Query("select l from Laboratory  l WHERE uuid = ?1")
    public Optional<Laboratory> getLabFromUuid(UUID uuid);

}
