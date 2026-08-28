package pl.visa.labmanager.substance;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubstanceController {

    private final SubstanceService substanceService;

    public SubstanceController(SubstanceService substanceService) {
        this.substanceService = substanceService;
    }


}
