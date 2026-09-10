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


    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContainerDtoOut>> getAllContainers() {
        return ResponseEntity.status(HttpStatus.OK).body(containerService.getAllContainersDtos());
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

    @PutMapping("/")
    public ResponseEntity editContainer(@RequestBody ContainerDtoIn dtoIn) {
        ContainerDtoOut dtoOut = containerService.editContainer(dtoIn);
        return ResponseEntity.ok().body(dtoOut);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteContainer(@PathVariable(name="uuid") UUID uuid) {
        containerService.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyśnie usunięto pojemnik o UUID = %s.".formatted(uuid));
    }






}
