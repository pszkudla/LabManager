package pl.visa.labmanager.substance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("select s from Substance s where s.smiles is not null")
    public List<Substance> getAllSubstancesWithSmiles();

    @Modifying
    @Query(value = "update substances set inchi_key = ?1 where uuid = ?2", nativeQuery = true)
    public void addInchiKeyToSubstance(String inchiKey, String uuid);

    @Query("select s from Substance s where s.inchi is null and s.inchiKey is null and s.smiles is not null and LENGTH(s.smiles) > 0")
    public List<Substance> findSubstancesWithMissingInchiData();

    @Query("select s from Substance s where s.isAromatic = true")
    public List<Substance> getAllAromaticSubstances();

    @Query("select s from Substance s where s.isEster = true")
    public List<Substance> getAllEsters();

    @Query("select s from Substance s where s.isCarboxylicAcid = true")
    public List<Substance> getAllCarboxylicAcids();


    public Optional<Substance> findSubstanceByCasNumber(String cas);


    @Query("select s from Substance s where s.smiles is not null and s.isCarboxylicAcid is null and s.isEster is null and s.isAromatic is null")
    public List<Substance> getAllSubstancesToFill();

    @Query("select distinct s from Substance s left join s.alternativeNames an where lower(s.iupacName) like lower(concat('%', ?1, '%'))  or lower(an.name) like lower(concat('%', ?1, '%'))")
    List<Substance> getSubstancesByIupacAndAltNames(String substring);
}
