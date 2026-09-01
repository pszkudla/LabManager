package pl.visa.labmanager.container;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.visa.labmanager.location.zone.ZoneService;

import java.util.List;

@RestController
@RequestMapping("/container")
public class ContainerController {
    private final ContainerService containerService;
    private final ZoneService zoneService;

    public ContainerController(ContainerService containerService, ZoneService zoneService) {
        this.containerService = containerService;
        this.zoneService = zoneService;
    }

    @GetMapping("/all")
    public List<ContainerDTO> getAllContainers() {
        return containerService.getAllContainers().stream()
                .map(Container::getDto).toList();
    }


}
