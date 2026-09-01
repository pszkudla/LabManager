package pl.visa.labmanager.substance;

import org.springframework.stereotype.Service;

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
}
