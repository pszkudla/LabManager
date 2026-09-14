package pl.visa.labmanager.location.lab;

import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/lab")
public class LabController {
    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @PostMapping("/")
    public ResponseEntity<LaboratoryDTO> addLab(@RequestBody Laboratory laboratory) {
        return ResponseEntity.status(HttpStatus.OK).body(labService.addLaboratory(laboratory));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LaboratoryDTO>> getAllLaboratories() {
        List<LaboratoryDTO> dtosToShow = labService.findAllLabs().stream().map(Laboratory::getLabDTO).toList();
        return ResponseEntity.ok().body(dtosToShow);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Laboratory> getLabDtoByUuid(@PathVariable(name="uuid") UUID uuid) {
        Laboratory lab = labService.getLabFromUuid(uuid);
        return ResponseEntity.ok().body(lab);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteLabByUuid(@PathVariable(name="uuid") UUID uuid) {
        boolean deleteSuccessfull = labService.deleteLab(uuid);
        if (deleteSuccessfull) {
            return ResponseEntity.ok().body("Pomyśnie usunięto laboratorium o uuid = %s".formatted(uuid));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie udało się usunąć laboratorium.");
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity editLab(@RequestBody LaboratoryDTO dto) {
        Optional<Laboratory> lab = labService.updateLaboratory(dto);
        if (lab.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Udało się zmodyfikować rekord.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie udało się zmodyfikować tego laboratorium.");
        }
    }


}
