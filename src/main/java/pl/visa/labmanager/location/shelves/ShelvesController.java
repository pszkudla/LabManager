package pl.visa.labmanager.location.shelves;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.errors.CabinetNotFoundError;
import pl.visa.labmanager.errors.ShelfNotFoundError;
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
    public List<ShelfDtoOut> getAllShelves() {
        return shelvesService.getAllShelveDTOS();
    }

    @GetMapping("/{uuid}")
    public Optional<ShelfDtoOut> getShelfDtoByUuid(@PathVariable(name="uuid") UUID uuid) {
        return shelvesService.findShelfDtoByUuid(uuid);
    }

    @PostMapping("/")
    public ResponseEntity addShelf(@RequestBody Map<String, String> map) {
        String cabinetUuid = map.get("cabinetUuid");
        UUID cabinetUuidAsUuid = UUID.fromString(cabinetUuid);
        String shelfName = map.get("shelfName");
        Optional<Cabinet> cabinet = cabinetService.findCabinetByUuid(cabinetUuidAsUuid);
        if (cabinet.isPresent()) {
            Shelf shelfToAdd = new Shelf();
            shelfToAdd.setShelfName(shelfName);
            shelfToAdd.setCabinet(cabinet.get());
            shelvesService.addShelf(shelfToAdd);
            return  ResponseEntity.ok().body("Pomyślnie dodano półkę.");
        } else {
            return  ResponseEntity.badRequest().body("Nie udało się dodać półki.");
        }

    }

    @PutMapping("/")
    public ResponseEntity editShelf(@RequestBody ShelfDtoIn shelfPostDTO) {
        try {
            ShelfDtoOut shelfDto = shelvesService.updateShelf(shelfPostDTO);
            return ResponseEntity.status(HttpStatus.OK).body(shelfDto);
        } catch (ShelfNotFoundError snfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(snfe.getMessage());
        } catch (CabinetNotFoundError cnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cnfe.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteShelf(@PathVariable(name="uuid") UUID uuid) {
        Optional<Shelf> optDeletedShelf = shelvesService.deleteShelfByUuid(uuid);
        if (optDeletedShelf.isPresent()) {
            Shelf deletedShelf = optDeletedShelf.get();
            return ResponseEntity.status(HttpStatus.OK).body(deletedShelf);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono półki o podanym UUID lub nie jest ona pusta.");
        }
    }







}
