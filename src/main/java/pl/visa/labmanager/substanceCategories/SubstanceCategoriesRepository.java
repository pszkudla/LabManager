package pl.visa.labmanager.substanceCategories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.visa.labmanager.substance.Substance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubstanceCategoriesRepository extends JpaRepository<SubstanceCategory, Long> {

    @Query("select sc from SubstanceCategory sc")
    public List<SubstanceCategory> findAllCategories();

    public Optional<SubstanceCategory> findSubstanceCategoriesByUuid(UUID uuid);

}
