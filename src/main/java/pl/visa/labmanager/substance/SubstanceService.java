package pl.visa.labmanager.substance;

import lombok.extern.slf4j.Slf4j;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.chemistryUtils.ChemistryUtils;
import pl.visa.labmanager.chemistryUtils.InchiData;
import pl.visa.labmanager.errors.InvalidSubstanceDescriptorException;
import pl.visa.labmanager.errors.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SubstanceService {
    private final SubstanceRepository substanceRepository;

    public SubstanceService(SubstanceRepository substanceRepository) {
        this.substanceRepository = substanceRepository;
    }


    public List<Substance> findNSubstances(int numberOfSubs) {
        return substanceRepository.getNSubstances(numberOfSubs);
    }

    public List<Substance> getSubstancesFromsubstring(String substring) {
        return substanceRepository.getSubstancesFromSubstring(substring);
    }

    public List<Substance> getSubstancesByCasFragment(String casSubs) {
        return substanceRepository.getSubstancesByCasFragment(casSubs);
    }

    public AlternativeSubstanceName addAlternativeName(String uuid, AlternativeSubstanceName asn) {
        Substance subs = substanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Nie odnaleziono substancki o UUID równym %s.".formatted(uuid))
                );
        subs.addAlternativeName(asn);
        substanceRepository.save(subs);
        return asn;
    }


    public void deleteSubstance(String uuid) {
        Substance substance = substanceRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie usuwania substancji.".formatted(uuid))
        );
        substanceRepository.delete(substance);
    }

    public SubstanceDtoOut updateSubstance(SubstanceDtoIn dtoIn) {
        Substance substanceToEdit = substanceRepository
                .findByUuid(dtoIn.getUuid())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s podczas prøby jej edycji.".formatted(dtoIn.getUuid())));
        substanceToEdit.setCasNumber(dtoIn.getCasNumber());
        substanceToEdit.setIupacName(dtoIn.getIupacName());
        substanceToEdit.setSmiles(dtoIn.getSmiles());
        return substanceRepository.save(substanceToEdit).getDtoOutFromSubstance();
    }


    public List<IAtomContainer> getAllAvailableMolecules() {
        List<IAtomContainer> substancesWithSmiles = substanceRepository.getAllSubstancesWithSmiles().stream().map(substance -> substance.getMolecule().get()).toList();
        return substancesWithSmiles;
    }

    @Async
    public void fillMissingInchiKeys() {
        List<Substance> substancesWithSmiles = substanceRepository.findSubstancesWithMissingInchiData();
        List<Substance> substancesToModify = new ArrayList<>();
        for (Substance substance : substancesWithSmiles) {
            String smiles = substance.getSmiles();
            try {
                Optional<InchiData> inchiData = ChemistryUtils.getInchiData(smiles);
                if (inchiData.isPresent()) {
                    substance.setInchi(inchiData.get().inchi());
                    substance.setInchiKey(inchiData.get().inchiKey());
                    substancesToModify.add(substance);
                }
            } catch (Exception e) {
                log.warn("Błąd przy parsowaniu %s.".formatted(smiles));
            }

        }
        substanceRepository.saveAll(substancesToModify);
    }


    public String checkIfContainsGroups(String substanceUuid) {
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        Substance substance = substanceRepository.findByUuid(substanceUuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie określenia obecności grup funkcyjnych.".formatted(substanceUuid)));
        String returnedString = "";
        try {
            IAtomContainer molecule = sp.parseSmiles(substance.getSmiles());

            SmartsPattern esterPattern = SmartsPattern.create("[C](=O)[O][#6]");
            Boolean isEster = esterPattern.matches(molecule);
            returnedString = returnedString + "IsEster = %s. ".formatted(isEster);
            substance.setIsEster(isEster);

            SmartsPattern carboxylicAcidPattern = SmartsPattern.create("[C](=O)[O;H1]");
            boolean isCarbAcid = carboxylicAcidPattern.matches(molecule);
            returnedString = returnedString + "IsCarboxylicAcid = %s. ".formatted(isCarbAcid);
            substance.setIsCarboxylicAcid(isCarbAcid);

            SmartsPattern aromaticPattern = SmartsPattern.create("c:c");
            boolean isAromatic = aromaticPattern.matches(molecule);
            returnedString = returnedString + "IsAromatic = %s. ".formatted(isAromatic);
            substance.setIsAromatic(isAromatic);

            substanceRepository.save(substance);


        } catch (InvalidSmilesException ise) {
            throw new InvalidSubstanceDescriptorException("Substancja o SMILES = %s nie istnieje.".formatted(substance.getSmiles()));
        }
        return returnedString;
    }

    @Async
    public void updateGroupsData() {
        List<Substance> substancesWithSmiles = substanceRepository.getAllSubstancesWithSmiles();
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        List<Substance> updatedSubstances = new ArrayList<>();
        for (Substance substance : substancesWithSmiles) {
            try {
                IAtomContainer molecule = sp.parseSmiles(substance.getSmiles());

                SmartsPattern esterPattern = SmartsPattern.create("[C](=O)[O][#6]");
                boolean isEster = esterPattern.matches(molecule);
                substance.setIsEster(isEster);

                SmartsPattern carboxylicAcidPattern = SmartsPattern.create("[C](=O)[O;H1]");
                boolean isCarbAcid = carboxylicAcidPattern.matches(molecule);
                substance.setIsCarboxylicAcid(isCarbAcid);

                SmartsPattern aromaticPattern = SmartsPattern.create("c:c");
                boolean isAromatic = aromaticPattern.matches(molecule);
                substance.setIsAromatic(isAromatic);

                updatedSubstances.add(substance);

            } catch (InvalidSmilesException ise) {
                throw new InvalidSubstanceDescriptorException("Substancja o SMILES = %s nie istnieje.".formatted(substance.getSmiles()));
            }
            substanceRepository.saveAll(updatedSubstances);
        }
    }


}
