package pl.visa.labmanager.substance;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;

import java.util.List;

@Service
public class SubstanceService {
    private final SubstanceRepository substanceRepository;

    public SubstanceService(SubstanceRepository substanceRepository) {
        this.substanceRepository = substanceRepository;
    }


    public List<Substance> findNSubstances(int numberOfSubs) {
        return substanceRepository.getNSubstances(numberOfSubs);
    }

    public List<Substance> getSubstancesFromsubstring(String substring) {
        return substanceRepository.getSubstancesFromSubstring(substring);
    }

    public List<Substance> getSubstancesByCasFragment(String casSubs) {
        return substanceRepository.getSubstancesByCasFragment(casSubs);
    }

    public AlternativeSubstanceName addAlternativeName(String uuid, AlternativeSubstanceName asn) {
        Substance subs = substanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Nie odnaleziono substancki o UUID równym %s.".formatted(uuid))
                );
        subs.addAlternativeName(asn);
        substanceRepository.save(subs);
        return asn;
    }


    public void deleteSubstance(String uuid) {
        Substance substance = substanceRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie usuwania substancji.".formatted(uuid))
        );
        substanceRepository.delete(substance);
    }
}
