package pl.visa.labmanager.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.container.Container;
import pl.visa.labmanager.container.ContainerDtoOut;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addRandomContainers")
    public ResponseEntity<List<ContainerDtoOut>> addRandomContainers(@RequestBody Map<String, String> map) {
        UUID zoneUuid = UUID.fromString(map.get("zoneUuid"));
        Integer numberOfContainers = Integer.parseInt(map.get("numberOfContainers"));
        String substanceCategory = map.get("subsCategory");

        List<ContainerDtoOut> dtos = adminService.addRandomContainers(substanceCategory, zoneUuid, numberOfContainers);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/addMissingRandomSuppliers")
    public ResponseEntity<String> addMissingRandomSuppliers() {
        adminService.addMissingSuppliers();
        return ResponseEntity.status(HttpStatus.OK).body("Dodano brakujących dostawców.");
    }

    @PostMapping("/addGroupsData")
    public ResponseEntity<Void> addGroupsData() {
        adminService.addSubstancesGroupData();
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/addAltNames")
    public ResponseEntity<Void> addAltNames() {
        adminService.addAltNames();
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/addFormulaAndWeight")
    public ResponseEntity<Void> addFormulaAndWeight() {
        adminService.addSubstanceFormulaAndWeight();
        return ResponseEntity.accepted().build();
    }


}
