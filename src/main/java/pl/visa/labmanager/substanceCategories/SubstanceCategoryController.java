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
    public SubstanceCategory addSubstanceCategory(@RequestBody SubstanceCategoryDtoIn dto) {
        SubstanceCategory createdCategory =  substanceCategoryService.addSubstanceCategory(dto.getName());
        return createdCategory;
    }

    @GetMapping("/{uuid}")
    public SubstanceCategoryDtoOut getSubstanceCategoryByUuid(@PathVariable(name="uuid") UUID uuid) {
        return substanceCategoryService.getSubstanceCategoryByUuid(uuid).getDto();
    }

    @GetMapping("/")
    public List<SubstanceCategoryDtoOut> getAllCategoiries() {
        return substanceCategoryService.getAllCategories();
    }


    @DeleteMapping("/{uuid}")
    public void deleteCategory(UUID uuid) {
        substanceCategoryService.deleteCategory(uuid);
    }

    @PostMapping("/addSubstanceToCategoryByCas")
    public ResponseEntity addSubstanceToCategoryByCas(@RequestBody Map<String, String> map) {
        String cas = map.get("cas");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.addSubstancesToCategoriesBySubstanceCas(cas, categoryUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyśnie dodano substancję do kategorii.");
    }

    @PostMapping("/addSubstanceToCategoryByUuid")
    public ResponseEntity addSubstanceToCategoryByUuid(@RequestBody Map<String, String> map) {
        String substanceUuid = map.get("substanceUuid");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.addSubstanceToCategoryByUuids(categoryUuid, substanceUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie dodano substancję do kategorii.");
    }

    @DeleteMapping("/deleteSubstanceFromCategory")
    public ResponseEntity deleteSubstanceFromCategory(@RequestBody Map<String, String> map) {
        String substanceUuid = map.get("substanceUuid");
        UUID categoryUuid = UUID.fromString(map.get("categoryUuid"));
        substanceCategoryService.deleteSubstanceFromCategory(substanceUuid, categoryUuid);
        return ResponseEntity.status(HttpStatus.OK).body("Pomyślnie usunięto substancję z kategorii.");
    }

    @PutMapping("/")
    public ResponseEntity changeSubstanceCategoryName(@RequestBody SubstanceCategoryDtoIn dtoIn) {
        SubstanceCategory sc =  substanceCategoryService.editSubstanceCategory(dtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }






}
