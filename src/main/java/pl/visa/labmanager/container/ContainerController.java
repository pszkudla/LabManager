package pl.visa.labmanager.container;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.visa.labmanager.location.zone.ZoneRepository;
import pl.visa.labmanager.location.zone.ZoneService;

@RestController
@RequestMapping("/container")
public class ContainerController {
    private final ContainerService containerService;
    private final ZoneService zoneService;

    public ContainerController(ContainerService containerService, ZoneService zoneService) {
        this.containerService = containerService;
        this.zoneService = zoneService;
    }


}
