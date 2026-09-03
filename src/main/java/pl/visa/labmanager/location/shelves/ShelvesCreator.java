package pl.visa.labmanager.location.shelves;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.location.cabinet.Cabinet;
import pl.visa.labmanager.location.cabinet.CabinetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/createShelves")
@RestController
public class ShelvesCreator {
    private final ShelvesService shelvesService;
    private final CabinetService cabinetService;

    public ShelvesCreator(ShelvesService shelvesService, CabinetService cabinetService) {
        this.shelvesService = shelvesService;
        this.cabinetService = cabinetService;
    }

    @PostMapping("/allCabinets/{numberOfShelves}")
    public void createInAllCabinets(@PathVariable(name="numberOfShelves") int numberOfShelves) {
        List<Cabinet> allCabinets = cabinetService.getAllCabinets();

        for (Cabinet cabinet : allCabinets) {
            for (int i = 1; i<=numberOfShelves; i++) {
                String nameOfShelf = "Półka numer %s".formatted(i);

                Shelf shelfToAdd = new Shelf();
                shelfToAdd.setShelfName(nameOfShelf);
                shelfToAdd.setCabinet(cabinet);
                shelvesService.addShelf(shelfToAdd);
            }
        }
    }

}
