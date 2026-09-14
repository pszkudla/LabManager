package pl.visa.labmanager.substanceCategories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/substanceCategory")
public class SubstanceCategoryController {
    private final SubstanceCategoryService substanceCategoryService;

    public SubstanceCategoryController(SubstanceCategoryService substanceCategoryService) {
        this.substanceCategoryService = substanceCategoryService;
    }

    @PostMapping("/")
    public ResponseEntity<SubstanceCategory> addSubstanceCategory(@RequestBody SubstanceCategoryDtoIn dto) {
        SubstanceCategory createdCategory =  substanceCategoryService.addSubstanceCategory(dto.getName());
        return ResponseEntity.status(HttpStatus.OK).body(createdCategory);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SubstanceCategoryDtoOut> getSubstanceCategoryByUuid(@PathVariable(name="uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(substanceCategoryService.getSubstanceCategoryByUuid(uuid).getDto());
    }

    @GetMapping("/")
    public ResponseEntity<List<SubstanceCategoryDtoOut>> getAllCategoiries() {
        return ResponseEntity.status(HttpStatus.OK).body(substanceCategoryService.getAllCategories());
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteCategory(UUID uuid) {
        substanceCategoryService.deleteCategory(uuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie usunięto kateogrię substancji o UUID = %s.".formatted(uuid));
    }

    @PostMapping("/addSubstanceToCategoryByCas")
    public ResponseEntity<String> addSubstanceToCategoryByCas(@RequestBody Map<String, String> map) {
        String cas = map.get("cas");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.addSubstancesToCategoriesBySubstanceCas(cas, categoryUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyśnie dodano substancję do kategorii.");
    }

    @PostMapping("/addSubstanceToCategoryByUuid")
    public ResponseEntity<String> addSubstanceToCategoryByUuid(@RequestBody Map<String, String> map) {
        String substanceUuid = map.get("substanceUuid");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.addSubstanceToCategoryByUuids(categoryUuid, substanceUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie dodano substancję do kategorii.");
    }

    @DeleteMapping("/deleteSubstanceFromCategory")
    public ResponseEntity<String> deleteSubstanceFromCategory(@RequestBody Map<String, String> map) {
        String substanceUuid = map.get("substanceUuid");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.deleteSubstanceFromCategory(substanceUuid, categoryUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie usunięto substancję z kategorii.");
    }

    @PutMapping("/")
    public ResponseEntity<SubstanceCategory> changeSubstanceCategoryName(@RequestBody SubstanceCategoryDtoIn dtoIn) {
        SubstanceCategory sc =  substanceCategoryService.editSubstanceCategory(dtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }






}
