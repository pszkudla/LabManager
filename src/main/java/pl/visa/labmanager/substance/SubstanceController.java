package pl.visa.labmanager.substance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/substances")
@RestController
public class SubstanceController {

    private final SubstanceService substanceService;

    public SubstanceController(SubstanceService substanceService) {
        this.substanceService = substanceService;
    }

    @GetMapping("/top/{n}")
    public List<Substance> getTop(@PathVariable(name="n") int number) {
        return substanceService.findNSubstances(number);
    }

    @GetMapping("/fromSubstring/{subs}")
    public List<Substance> getFromSubs(@PathVariable(name="subs") String substring) {
        return substanceService.getSubstancesFromsubstring(substring);
    }

    @GetMapping("/fromCas/{casSubs}")
    public List<Substance> getByCasFragment(@PathVariable(name="casSubs") String casSubstring) {
        System.out.println(casSubstring);
        return substanceService.getSubstancesByCasFragment(casSubstring);
    }



}
