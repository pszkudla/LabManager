package pl.visa.labmanager.safetyDataSheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SDS_Repository extends JpaRepository<SafetyDataSheet, Long> {

    public Optional<SafetyDataSheet> findSafetyDataSheetByUuid(UUID uuid);

    @Query(value = "SELECT * FROM safety_data_sheets sds JOIN substances s ON sds.substance_id = s.id WHERE s.uuid = ?1", nativeQuery = true)
    public List<SafetyDataSheet> findAllSdsBySubstanceUuid(String substanceUUID);
}
