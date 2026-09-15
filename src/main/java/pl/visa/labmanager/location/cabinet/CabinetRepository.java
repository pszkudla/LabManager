package pl.visa.labmanager.location.cabinet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.visa.labmanager.location.lab.Laboratory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {

    @Query("select c FROM Cabinet c where uuid = ?1")
    public Optional<Cabinet> getCabinetByUuid(UUID uuidString);



    @Query("select c from Cabinet c WHERE laboratory = ?1")
    public List<Cabinet> getAllCabinetsInLab(Laboratory laboratory);

}
