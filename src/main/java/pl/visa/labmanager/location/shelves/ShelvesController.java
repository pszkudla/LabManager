package pl.visa.labmanager.location.shelves;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.location.cabinet.Cabinet;
import pl.visa.labmanager.location.cabinet.CabinetService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shelf")
public class ShelvesController {

    private final ShelvesService shelvesService;
    private final CabinetService cabinetService;

    public ShelvesController(ShelvesService shelvesService, CabinetService cabinetService) {
        this.shelvesService = shelvesService;
        this.cabinetService = cabinetService;
    }

    @GetMapping("/all")
    public List<Shelf> getAllShelves() {
        return shelvesService.getAllShelves();
    }

    @PostMapping("/")
    public ResponseEntity addShelf(@RequestBody Map<String, String> map) {
        String cabinetUuid = map.get("cabinetUuid");
        UUID cabinetUuidAsUuid = UUID.fromString(cabinetUuid);
        String shelfName = map.get("shelfName");
        Optional<Cabinet> cabinet = cabinetService.getByUuid(cabinetUuidAsUuid);
        if (cabinet.isPresent()) {
            Shelf shelfToAdd = new Shelf();
            shelfToAdd.setShelfName(shelfName);
            shelfToAdd.setCabinet(cabinet.get());
            shelvesService.addShelf(shelfToAdd);
            return  ResponseEntity.ok().body("OK");
//            return new ResponseEntity.ok("Pomyślnie dodano półkę.");
        } else {
            return  ResponseEntity.badRequest().body("Nie udało się dodać półki.");
        }

    }







}
