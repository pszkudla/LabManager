package pl.visa.labmanager.container;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.visa.labmanager.location.zone.ZoneService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/container")
public class ContainerController {
    private final ContainerService containerService;
    private final ZoneService zoneService;
    private final ContainerRepository containerRepository;

    public ContainerController(ContainerService containerService, ZoneService zoneService, ContainerRepository containerRepository) {
        this.containerService = containerService;
        this.zoneService = zoneService;
        this.containerRepository = containerRepository;
    }

    @GetMapping("/all")
    public List<ContainerDtoOut> getAllContainers() {
        return containerService.getAllContainers().stream()
                .map(Container::getDtoOut).toList();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getContainerByUuid(@PathVariable(name="uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(containerService.getContainerByUuid(uuid));
    }


    @PostMapping("/")
    public ResponseEntity addContainer(@RequestBody ContainerDtoIn dto) {
        ContainerDtoOut containerDTO = containerService.createContainer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(containerDTO);
    }


}
