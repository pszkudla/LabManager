package pl.visa.labmanager.safetyDataSheet;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sds")
public class SDS_Controller {

    private final SDS_Service sdsService;

    public SDS_Controller(SDS_Service sdsService) {
        this.sdsService = sdsService;
    }

    @GetMapping("/all")
    public List<SafetyDataSheet> getAllDataSheets() {
        return sdsService.getAllSds();
    }

    @PostMapping("/")
    public void addSds(@RequestBody SDS_DTO_in dtoIn) {
        sdsService.addSDS(dtoIn);
    }
}
