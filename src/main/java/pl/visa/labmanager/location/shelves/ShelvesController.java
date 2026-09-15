package pl.visa.labmanager.location.shelves;

import org.springframework.http.HttpStatus;
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

    public ShelvesController(ShelvesService shelvesService) {
        this.shelvesService = shelvesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShelfDtoOut>> getAllShelves() {
        return ResponseEntity.status(HttpStatus.OK).body(shelvesService.getAllShelveDTOS());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Optional<ShelfDtoOut>> getShelfDtoByUuid(@PathVariable(name="uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(shelvesService.findShelfDtoByUuid(uuid));
    }

    @PostMapping("/")
    public ResponseEntity<ShelfDtoOut> addShelf(@RequestBody Map<String, String> map) {
        ShelfDtoOut dto = shelvesService.createShelf(map);
        return  ResponseEntity.ok().body(dto);
    }

    @PutMapping("/")
    public ResponseEntity<ShelfDtoOut> editShelf(@RequestBody ShelfDtoIn shelfPostDTO) {
        ShelfDtoOut shelfDto = shelvesService.updateShelf(shelfPostDTO);
        return ResponseEntity.status(HttpStatus.OK).body(shelfDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteShelf(@PathVariable(name="uuid") UUID uuid) {
        shelvesService.deleteShelfByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie usunięto półkę o UUID = %s.".formatted(uuid));
    }


    @GetMapping("/allShelvesByCabinetUuid/{uuid}")
    public ResponseEntity<List<ShelfDtoOut>> getAllShelvesByCabinetUuid(@PathVariable(name="uuid") UUID cabinetUuid) {
        List<ShelfDtoOut> shelves = shelvesService.getAllShelvesFromCabinet(cabinetUuid).stream().map(Shelf::getShelfDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(shelves);
    }







}
