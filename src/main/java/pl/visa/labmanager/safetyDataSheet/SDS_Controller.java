package pl.visa.labmanager.safetyDataSheet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<List<SafetyDataSheet>> getAllDataSheets() {
            return ResponseEntity.status(HttpStatus.OK).body(sdsService.getAllSds());
        }

        @PostMapping("/")
        public ResponseEntity<SafetyDataSheet> addSds(@RequestBody SDS_DTO dtoIn) {
            return ResponseEntity.status(HttpStatus.OK).body(sdsService.addSDS(dtoIn));
        }

        @GetMapping("/{uuid}")
        public ResponseEntity<SafetyDataSheet> getByUuid(@PathVariable(name="uuid") UUID uuid) {
            return ResponseEntity.status(HttpStatus.OK).body(sdsService.findByUuid(uuid));
        }

        @DeleteMapping("/{uuid}")
        public ResponseEntity<String> deleteByUuid(@PathVariable(name="uuid") UUID uuid) {
            sdsService.deleteByUuid(uuid);
            return ResponseEntity.status(HttpStatus.OK).body("Udało się usunąć kartę charakterystyki z bazy danych.");
        }

        @GetMapping("/bySubstanceUuid/{substanceUuid}")
        public ResponseEntity<List<SafetyDataSheet>> getAllSdsOfSubstance(@PathVariable(name="substanceUuid") String substanceUuid) {
            return ResponseEntity.status(HttpStatus.OK).body(sdsService.findAllSdsOfSubstance(substanceUuid));
        }

        @PutMapping("/")
        public  ResponseEntity<SDS_DTO> editSds(@RequestBody SDS_DTO sdsDto) {

        return ResponseEntity.status(HttpStatus.OK).body(sdsService.editSds(sdsDto));
        }





}
