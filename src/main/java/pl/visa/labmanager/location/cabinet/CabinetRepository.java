package pl.visa.labmanager.location.cabinet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {

    @Query("select c FROM Cabinet c where uuid = ?1")
    public Optional<Cabinet> getCabinetByUuid(UUID uuidString);



}
