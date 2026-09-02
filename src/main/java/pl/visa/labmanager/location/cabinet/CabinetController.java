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
    public List<CabinetDTO> getAllCabinets() {
        return cabinetService.getAllCabinets().stream()
                .map(Cabinet::getDTO).toList();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getCabinetByUuid(@PathVariable(name="uuid") UUID uuid) {
        Optional<CabinetDTO> cabinetDTO = cabinetService.getDtoByUuid(uuid);
        if (cabinetDTO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cabinetDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono szafy o podanym UUID.");
        }
    }

    @PostMapping("/")
    public void addCabinet(@RequestBody Map<String, String> map){
        Cabinet addedCabinet = new Cabinet();
        addedCabinet.setCabinetName(map.get("cabinetName"));
        Optional<Laboratory> cabinetLabOpt = labService.getLabFromUuid(UUID.fromString(map.get("labUuid")));
        if (cabinetLabOpt.isPresent()) {
            Laboratory cabinetLab = cabinetLabOpt.get();
            addedCabinet.setLaboratory(cabinetLab);
            cabinetService.addCabinet(addedCabinet);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteCabinet(@PathVariable(name="uuid") UUID uuid) {
        Optional<Cabinet> cabinet = cabinetService.deleteByUuid(uuid);
        if (cabinet.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Zmodyfikowano szafkę o UUID = %s.".formatted(uuid));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono szafki o podanym UUID.");
        }
    }



}
