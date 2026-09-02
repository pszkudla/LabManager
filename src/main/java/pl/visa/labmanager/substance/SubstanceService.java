package pl.visa.labmanager.substance;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String addAlternativeName(String uuid, AlternativeSubstanceName asn) {
        Optional<Substance> substOpt = substanceRepository.findByUuid(uuid);
        if (substOpt.isPresent()) {
            Substance substance = substOpt.get();
            substance.addAlternativeName(asn);
            String iupacName = substance.getIupacName();
            substanceRepository.save(substance);
            return "Do %s dodano alternatywną nazwę - %s.".formatted(iupacName, asn.getName());
        } else {
            return "Nie udało się dodać alternatywnej nazwy.";
        }
    }
}
