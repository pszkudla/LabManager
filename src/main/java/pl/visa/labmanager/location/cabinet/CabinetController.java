package pl.visa.labmanager.location.cabinet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.location.lab.LabService;
import pl.visa.labmanager.location.lab.Laboratory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    private final CabinetService cabinetService;
    private final LabService labService;

    public CabinetController(CabinetService cabinetService, LabService labService) {
        this.cabinetService = cabinetService;
        this.labService = labService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CabinetDtoOut>> getAllCabinets() {
        return ResponseEntity.status(HttpStatus.OK).body(cabinetService.getAllCabinetDtos());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CabinetDtoOut> getCabinetByUuid(@PathVariable(name="uuid") UUID uuid) {
        CabinetDtoOut cabinet = cabinetService.getCabinetDtoByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(cabinet);
    }

    @PostMapping("/")
    public ResponseEntity<CabinetDtoOut> addCabinet(@RequestBody Map<String, String> map) {
        CabinetDtoOut dto = cabinetService.addCabinet(map);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteCabinet(@PathVariable(name="uuid") UUID uuid) {
        Optional<Cabinet> cabinet = cabinetService.deleteByUuid(uuid);
        if (cabinet.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Zmodyfikowano szafkę o UUID = %s.".formatted(uuid));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono szafki o podanym UUID.");
        }
    }

    @PutMapping("/")
    public ResponseEntity<CabinetDtoOut> updateCabinet(@RequestBody CabinetDtoIn cabinetDtoIn) {
        return ResponseEntity.status(HttpStatus.OK).body(cabinetService.updateCabinet(cabinetDtoIn));
    }

    @GetMapping("/byLabUuid/{labUuid}")
    public ResponseEntity<List<CabinetDtoOut>> getCabinetsByLabUuid(@PathVariable UUID labUuid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        cabinetService.getAllCabinetsInLab(labUuid).stream()
                                .map(Cabinet::getDtoOut)
                                .toList()
                );
    }



}
