package pl.visa.labmanager.location.zone;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.visa.labmanager.location.shelves.Shelf;
import pl.visa.labmanager.location.shelves.ShelvesService;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/zoneCreator")
public class ZonesCreator {
    private final ZoneService zoneService;
    private final ShelvesService shelvesService;
    private final Random random = new Random();
    private final String alphabetString = "abcdefghijklmnopqrstuvwxyz";

    public ZonesCreator(ZoneService zoneService, ShelvesService shelvesService) {
        this.shelvesService = shelvesService;
        this.zoneService = zoneService;
    }

    @PostMapping("/createZonesInAllShelves/{min}/{max}")
    public void createZones(@PathVariable(name = "min") int min, @PathVariable(name = "max") int max) {
        List<Shelf> allShelves = shelvesService.getAllShelves();
        for (Shelf shelf : allShelves) {
            String[] shelfNameParts = shelf.getShelfName().split(" ");
            String lastShelfNamePart = shelfNameParts[shelfNameParts.length - 1];
            int numberOfZones = random.nextInt(max - min + 1) + min;
            for (int i = 1; i <= numberOfZones; i++) {
                char zoneSymbol = alphabetString.charAt(i-1);
                String zoneName = "Strefa %s.%s".formatted(lastShelfNamePart, zoneSymbol);
                Zone zoneToAdd = new Zone();
                zoneToAdd.setZoneName(zoneName);
                zoneToAdd.setShelf(shelf);
                zoneService.addZone(zoneToAdd);
            }
        }
    }
}
