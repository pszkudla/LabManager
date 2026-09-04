package pl.visa.labmanager.safetyDataSheet;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sds")
public class SDS_Controller {

    private final SDS_Service sdsService;

    public SDS_Controller(SDS_Service sdsService) {
        this.sdsService = sdsService;
    }

        @GetMapping("/all")
        public List<SafetyDataSheet> getAllDataSheets() {
            return sdsService.getAllSds();
        }

        @PostMapping("/")
        public void addSds(@RequestBody SDS_DTO_in dtoIn) {
            sdsService.addSDS(dtoIn);
        }

        @GetMapping("/{uuid}")
        public SafetyDataSheet getByUuid(@PathVariable(name="uuid") UUID uuid) {
            return sdsService.findByUuid(uuid);
        }

        @DeleteMapping("/{uuid}")
        public void deleteByUuid(@PathVariable(name="uuid") UUID uuid) {
            sdsService.deleteByUuid(uuid);
        }

        @GetMapping("/bySubstanceUuid/{substanceUuid}")
        public List<SafetyDataSheet> getAllSdsOfSubstance(@PathVariable(name="substanceUuid") String substanceUuid) {
            return sdsService.findAllSdsOfSubstance(substanceUuid);
        }



}
