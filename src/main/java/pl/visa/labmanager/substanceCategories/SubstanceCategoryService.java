package pl.visa.labmanager.substanceCategories;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.substance.Substance;
import pl.visa.labmanager.substance.SubstanceRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SubstanceCategoryService {
    private final SubstanceCategoriesRepository substanceCategoriesRepository;
    private final SubstanceRepository substanceRepository;

    public SubstanceCategoryService(SubstanceCategoriesRepository substanceCategoriesRepository, SubstanceRepository substanceRepository) {
        this.substanceCategoriesRepository = substanceCategoriesRepository;
        this.substanceRepository = substanceRepository;
    }

    public SubstanceCategory addSubstanceCategory(String name) {
        SubstanceCategory categoryToAdd = new SubstanceCategory();
        categoryToAdd.setName(name);
        substanceCategoriesRepository.save(categoryToAdd);
        return categoryToAdd;
    }

    public List<SubstanceCategoryDtoOut> getAllCategories() {
        return substanceCategoriesRepository.findAllCategories().stream().map(substanceCategory -> substanceCategory.getDto()).toList();
    }

    public SubstanceCategory getSubstanceCategoryByUuid(UUID uuid) {
        SubstanceCategory category = substanceCategoriesRepository
                .findSubstanceCategoriesByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kategorii substancji o UUID = %s.".formatted(uuid)));
        return category;
    }

    public void deleteCategory(UUID uuid) {
        SubstanceCategory categoryToDelete = substanceCategoriesRepository.findSubstanceCategoriesByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie udało się znaleźć kategorii substancji o UUID = %s.".formatted(uuid)));
        substanceCategoriesRepository.delete(categoryToDelete);
    }

    public void addSubstanceToCategoryByUuids(UUID categoryUuid, String substanceUuid) {
        Substance substance = substanceRepository.findByUuid(substanceUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie dodania substancji do kategorii przez UUID.".formatted(substanceUuid)));
        SubstanceCategory substanceCategory = substanceCategoriesRepository.findSubstanceCategoriesByUuid(categoryUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kategorii o UUID = %s przy próbie dodania substancji do kategorii.".formatted(categoryUuid)));
        substanceCategory.getSubstancesInCategory().add(substance);
        substanceCategoriesRepository.save(substanceCategory);
    }

    public void addSubstancesToCategoriesBySubstanceCas(String cas, UUID categoryUuid) {
        Substance substance = substanceRepository.findSubstanceByCasNumber(cas).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o CAS = %s podczas próby dodania substancji do kateogrii.".formatted(cas)));
        SubstanceCategory substanceCategory = substanceCategoriesRepository.findSubstanceCategoriesByUuid(categoryUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kategorii o UUID = %s przy próbie dodania substancji do kategorii.".formatted(categoryUuid)));
        substanceCategory.getSubstancesInCategory().add(substance);
        substanceCategoriesRepository.save(substanceCategory);
    }


    public void deleteSubstanceFromCategory(String substanceUuid, UUID categoryUuid) {
        SubstanceCategory category = substanceCategoriesRepository.findSubstanceCategoriesByUuid(categoryUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kategorii substancji o UUID = %s przy próbie usunięcia substancji z kategorii.".formatted(categoryUuid)));
        Substance substance = substanceRepository.findByUuid(substanceUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie usunięcia substancji z kategorii.".formatted(substanceUuid)));
        category.getSubstancesInCategory().remove(substance);
        substanceCategoriesRepository.save(category);
    }

    public SubstanceCategory editSubstanceCategory(SubstanceCategoryDtoIn dtoIn) {
        SubstanceCategory categoryToEdit = substanceCategoriesRepository
                .findSubstanceCategoriesByUuid(dtoIn.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kategorii substancji o UUID = %s podczas próby jej edycji.".formatted(dtoIn.getUuid())));
        categoryToEdit.setName(dtoIn.getName());
        return substanceCategoriesRepository.save(categoryToEdit);
    }



}
