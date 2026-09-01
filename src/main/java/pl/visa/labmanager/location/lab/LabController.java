package pl.visa.labmanager.location.lab;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lab")
public class LabController {
    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @PostMapping("/")
    public void addLab(@RequestBody Laboratory laboratory) {
        labService.addLaboratory(laboratory.getLaboratoryName());
    }

}
