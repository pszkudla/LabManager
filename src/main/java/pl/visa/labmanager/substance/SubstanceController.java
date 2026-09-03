package pl.visa.labmanager.substance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable(name="uuid") String uuid) {
        substanceService.deleteSubstance(uuid);
    }

    @PostMapping("/addAltName")
    public ResponseEntity addAltName(@RequestBody Map<String, String> map) {
        String language = map.get("language");
        String newAltName = map.get("name");
        String uuid = map.get("uuid");
        AlternativeSubstanceName asn = new AlternativeSubstanceName();
        asn.setName(newAltName);
        asn.setLanguage(language);
        return ResponseEntity.ok(substanceService.addAlternativeName(uuid, asn));
    }
}
