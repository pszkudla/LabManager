package pl.visa.labmanager.substance;

import org.springframework.stereotype.Service;

@Service
public class SubstanceService {
    private final SubstanceRepository substanceRepository;

    public SubstanceService(SubstanceRepository substanceRepository) {
        this.substanceRepository = substanceRepository;
    }


}
