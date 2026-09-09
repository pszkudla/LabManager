package pl.visa.labmanager.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity addRandomContainers(@RequestBody Map<String, String> map) {
        UUID zoneUuid = UUID.fromString(map.get("zoneUuid"));
        Integer numberOfContainers = Integer.parseInt(map.get("numberOfContainers"));
        String substanceCategory = map.get("subsCategory");


        List<ContainerDtoOut> dtos = adminService.addRandomContainers(substanceCategory, zoneUuid, numberOfContainers);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/addMissingRandomSuppliers")
    public ResponseEntity addMissingRandomSuppliers() {
        adminService.addMissingSuppliers();
        return ResponseEntity.status(HttpStatus.OK).body("Dodano brakujących dostawców.");
    }

    @PostMapping("/addGroupsData")
    public ResponseEntity addGroupsData() {
        adminService.addSubstancesGroupData();
        return ResponseEntity.accepted().build();
    }
}
