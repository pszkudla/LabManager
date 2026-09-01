package pl.visa.labmanager.location.zone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.visa.labmanager.location.shelves.Shelf;
import pl.visa.labmanager.location.shelves.ShelvesService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/zone")
public class ZoneController {
    private final ZoneService zoneService;
    private final ShelvesService shelvesService;
    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneService zoneService, ShelvesService shelvesService, ZoneRepository zoneRepository) {
        this.shelvesService = shelvesService;
        this.zoneService = zoneService;
        this.zoneRepository = zoneRepository;
    }

    @GetMapping("/")
    public ResponseEntity getAllZones() {
        return ResponseEntity.ok().body(zoneService.getAllZones());
    }

    @PostMapping("/")
    public ResponseEntity addZone(Map<String, String> map) {
        String shelfUuid = map.get("shelfUuid");
        String zoneName = map.get("zoneName");
        UUID shelfUuidAsUuid = UUID.fromString(shelfUuid);
        Optional<Shelf> optShelf = shelvesService.findShelfByUuid(shelfUuidAsUuid);
        if (optShelf.isPresent() && !zoneName.isBlank()) {
            Zone zoneToAdd = new Zone();
            zoneToAdd.setZoneName(zoneName);
            zoneToAdd.setShelf(optShelf.get());
            zoneRepository.save(zoneToAdd);
            return ResponseEntity.ok().body("Pomyślnie zapisano obiekt.");
        } else if (zoneName.isBlank()) {
            return ResponseEntity.badRequest().body("Nazwa strefy nie może być pusta.");
        } else if (optShelf.isEmpty()) {
            return ResponseEntity.badRequest().body("Nie odnaleziono półki o podanym UUID.");
        } else {
            return ResponseEntity.badRequest().body("Natrafiono na nieznany błąd.");
        }
    }

}
