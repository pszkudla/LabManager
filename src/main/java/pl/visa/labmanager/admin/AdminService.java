package pl.visa.labmanager.admin;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.container.Container;
import pl.visa.labmanager.container.ContainerDtoOut;
import pl.visa.labmanager.container.ContainerRepository;
import pl.visa.labmanager.errors.InvalidSubstanceDescriptorException;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.location.zone.Zone;
import pl.visa.labmanager.location.zone.ZoneRepository;
import pl.visa.labmanager.substance.AlternativeSubstanceName;
import pl.visa.labmanager.substance.Substance;
import pl.visa.labmanager.substance.SubstanceRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class AdminService {
    private final SubstanceRepository substanceRepository;
    private final ContainerRepository containerRepository;
    private final ZoneRepository zoneRepository;


    public AdminService(SubstanceRepository substanceRepository, ContainerRepository containerRepository, ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
        this.containerRepository = containerRepository;
        this.substanceRepository = substanceRepository;
    }

    public List<ContainerDtoOut> addRandomContainers(String groupName, UUID zoneUuid, int numberOfContainers) {
        List<Substance> substancesList = new ArrayList<>();
        List<Container> addedContainers = new ArrayList<>();

        Random rand = new Random();
        Zone zone = zoneRepository
                .findByUuid(zoneUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono strefy o UUID = %s.".formatted(zoneUuid)));

        List<String> purityOptions = List.of("cz.", "cz. d. a.", "pure", "spektralnie czysty");
        List<Integer> capacitiesNumbersOptions = List.of(10, 1, 2, 5, 50, 25, 100, 250, 200, 1000);
        List<String> unitOptions = List.of("mg", "g", "ml");
        List<String> suppliers = List.of("Merck", "Sigma-Aldrich", "Thermo Fisher Scientific", "Alfa Aesar", "Carl Roth", "Fisher Chemical", "Chempur", "POCH", "Fluka", "ABCR", "Fluorochem");

        if (groupName.equals("aromatic")) {
            substancesList = substanceRepository.getAllAromaticSubstances();
        } else if (groupName.equals("ester")) {
            substancesList = substanceRepository.getAllEsters();
        } else if (groupName.equals("carboxylicAcid")) {
            substancesList = substanceRepository.getAllCarboxylicAcids();
        }

        if (substancesList.size() > 0) {
            for (int i = 0; i < numberOfContainers; i++) {
                Substance substance = substancesList.get(rand.nextInt(substancesList.size()));



                String purity = purityOptions.get(rand.nextInt(purityOptions.size()));
                Integer capacityNumber = capacitiesNumbersOptions.get(rand.nextInt(capacitiesNumbersOptions.size()));
                String unit = unitOptions.get(rand.nextInt(unitOptions.size()));

                String capaityString = "%s %s".formatted(capacityNumber, unit);

                Container container = new Container();
                container.setZone(zone);
                container.setSubstance(substance);
                container.setPurity(purity);
                container.setCapacity(capaityString);

                String supplier = suppliers.get(rand.nextInt(suppliers.size()));
                container.setSupplier(supplier);

                addedContainers.add(container);
            }

            containerRepository.saveAll(addedContainers);
        }
        List<ContainerDtoOut> dtoOuts = addedContainers.stream().map(Container::getDtoOut).toList();

        return dtoOuts;
    }


    public void addMissingSuppliers() {
        Random rand = new Random();
        List<Container> containersWithoutSupplier = containerRepository.getAllContainersWithoutSupplier();
        List<Container> updatedContainers = new ArrayList<>();
        List<String> suppliers = List.of("Merck", "Sigma-Aldrich", "Thermo Fisher Scientific", "Alfa Aesar", "Carl Roth", "Fisher Chemical", "Chempur", "POCH", "Fluka", "ABCR", "Fluorochem");

        for (Container container: containersWithoutSupplier) {
            String supplier = suppliers.get(rand.nextInt(suppliers.size()));
            container.setSupplier(supplier);
            updatedContainers.add(container);
        }

        containerRepository.saveAll(updatedContainers);
    }

    @Async
    public void addSubstancesGroupData() {
        List<Substance> substancesToEdit = substanceRepository.getAllSubstancesToFill();
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        List<Substance> editList = new ArrayList<>();
        for (Substance substance : substancesToEdit) {
            String smiles = substance.getSmiles();
            try {
                IAtomContainer molecule = sp.parseSmiles(smiles);

                SmartsPattern esterPattern = SmartsPattern.create("[C](=O)[O][#6]");
                boolean isEster = esterPattern.matches(molecule);
                substance.setIsEster(isEster);

                SmartsPattern carboxylicAcidPattern = SmartsPattern.create("[C](=O)[O;H1]");
                boolean isCarbAcid = carboxylicAcidPattern.matches(molecule);
                substance.setIsCarboxylicAcid(isCarbAcid);

                SmartsPattern aromaticPattern = SmartsPattern.create("c:c");
                boolean isAromatic = aromaticPattern.matches(molecule);
                substance.setIsAromatic(isAromatic);
                editList.add(substance);
            } catch (InvalidSmilesException ise) {
                throw new InvalidSubstanceDescriptorException("Substancja o SMILES = %s nie istnieje.".formatted(substance.getSmiles()));
            }
        }
        substanceRepository.saveAll(editList);
    }



    @Async
    public void addAltNames() {
        try (CSVReader reader = new CSVReader(new FileReader("altNamesData.csv"))) {

            List<String[]> rows = reader.readAll();

            List<Substance> substancesToEdit = new ArrayList<>();


            for (String[] row : rows) {
                String plName = row[1];
                String enName = row[2];
                String cas = row[3];

//                System.out.println("%s | %s | %s".formatted(cas, plName, enName));
                Optional<Substance> optSubstance = substanceRepository.findSubstanceByCasNumber(cas);

                if (optSubstance.isPresent()) {
                    Substance substance = optSubstance.get();
                    String iupacName = substance.getIupacName();
                    if (iupacName != plName) {
                        AlternativeSubstanceName altName = new AlternativeSubstanceName();
                        altName.setLanguage("pl");
                        altName.setName(plName);
                        Set<AlternativeSubstanceName> altNames = substance.getAlternativeNames();
                        altNames.add(altName);
                    }

                    if (iupacName != enName) {
                        AlternativeSubstanceName altName = new AlternativeSubstanceName();
                        altName.setLanguage("en");
                        altName.setName(enName);
                        Set<AlternativeSubstanceName> altNames = substance.getAlternativeNames();
                        altNames.add(altName);
                    }

                    substancesToEdit.add(substance);
                }
            }
            substanceRepository.saveAll(substancesToEdit);

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundExcerption");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ioException");
            e.printStackTrace();
        } catch (CsvException e) {
            System.out.println("csv exception");
            e.printStackTrace();
        }
    }


}
