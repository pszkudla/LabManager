package pl.visa.labmanager.substance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubstanceRepository extends JpaRepository<Substance, Long> {


    @Query(value = "select * FROM substances LIMIT ?1", nativeQuery = true)
    List<Substance> getNSubstances(int numberOfSubstances);

    @Query("SELECT s FROM Substance s WHERE s.iupacName LIKE %:subs% ORDER BY length(s.iupacName)")
    List<Substance> getSubstancesFromSubstring(String subs);

    @Query("select s from Substance s where s.casNumber LIKE %:casFragment%")
    List<Substance> getSubstancesByCasFragment(String casFragment);

    public Optional<Substance> findByUuid(String uuid);

}
