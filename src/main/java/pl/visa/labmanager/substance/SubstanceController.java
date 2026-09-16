package pl.visa.labmanager.substance;

import lombok.Getter;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Substance>> getTop(@PathVariable(name="n") int number) {
        return ResponseEntity.status(HttpStatus.OK).body(substanceService.findNSubstances(number));
    }

    @GetMapping("/fromSubstring/{subs}")
    public ResponseEntity<List<SubstanceDtoOut>> getFromSubs(@PathVariable(name="subs") String substring) {
        return ResponseEntity.status(HttpStatus.OK).body(substanceService.getSubstanceDtosFromsubstring(substring));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SubstanceDtoOut> getFromUuid(@PathVariable(name="uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(substanceService.getSubstanceByUuid(uuid));
    }

    @GetMapping("/fromCas/{casSubs}")
    public ResponseEntity<List<Substance>> getByCasFragment(@PathVariable(name="casSubs") String casSubstring) {
        System.out.println(casSubstring);
        return ResponseEntity.status(HttpStatus.OK).body(substanceService.getSubstancesByCasFragment(casSubstring));
    }

    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable(name="uuid") String uuid) {
        substanceService.deleteSubstance(uuid);
    }

    @PostMapping("/addAltName")
    public ResponseEntity<AlternativeSubstanceName> addAltName(@RequestBody Map<String, String> map) {
        return ResponseEntity.ok(substanceService.addAltName(map));
    }

    @PutMapping("/")
    public ResponseEntity<SubstanceDtoOut> editSubstance(@RequestBody SubstanceDtoIn dtoIn) {
        SubstanceDtoOut dtoOut = substanceService.updateSubstance(dtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(dtoOut);
    }


    @PostMapping("/admin/addMissingInchiData")
    public ResponseEntity<Void> addMissingInchiData() {
        substanceService.fillMissingInchiKeys();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/check-groups/{uuid}")
    public ResponseEntity<String> checkSubstanceGroups(@PathVariable(name="uuid") String uuid) {
        String response = substanceService.checkIfContainsGroups(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/update-all-substances")
    public ResponseEntity<Void> updateAllSubstances() {
        substanceService.updateGroupsData();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/allNamesBySubstring/{subs}")
    public ResponseEntity<List<SubstanceDtoOut>> getByIupacAndAltNames(@PathVariable(name="subs") String subs) {
        List<SubstanceDtoOut> allSubstances = substanceService.getSubstncesByIupacAndAltNames(subs);
        return ResponseEntity.status(HttpStatus.OK).body(allSubstances);
    }

    @GetMapping("/getByNamesNativeQuery/{substring}")
    public ResponseEntity<List<SubstanceDtoOut>> getSubsByNamesNative(@PathVariable(name="substring") String substring) {
        List<SubstanceDtoOut> substances = substanceService.getSubstancesByNameNativeQuery(substring).stream().map(Substance::getDtoOutFromSubstance).toList();
        return ResponseEntity.status(HttpStatus.OK).body(substances);
    }
}
