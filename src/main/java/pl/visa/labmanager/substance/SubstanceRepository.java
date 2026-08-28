package pl.visa.labmanager.substance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstanceRepository extends JpaRepository<Substance, Long> {
}
