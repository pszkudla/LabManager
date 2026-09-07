package pl.visa.labmanager.chemistryUtils;

import lombok.extern.slf4j.Slf4j;
import org.openscience.cdk.debug.DebugChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.InvalidStructureException;

import java.util.Optional;

@Slf4j
@Service
public class ChemistryUtils {

    public static IAtomContainer parseSmilesToAtomContainer(String smiles) {
        SmilesParser smilesParser = new SmilesParser(DebugChemObjectBuilder.getInstance());
        try {
            return smilesParser.parseSmiles(smiles);
        } catch (InvalidSmilesException e) {
            throw new InvalidStructureException("Nie udało sie sparsować SMILES = %s.".formatted(smiles));
        }

    }


    public static Optional<String> getInchiKey(String smiles) {
        SmilesParser smilesParser = new SmilesParser(DebugChemObjectBuilder.getInstance());
        try {
            IAtomContainer molecule = smilesParser.parseSmiles(smiles);
            InChIGenerator inchiGenertor = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule);
            String inchiKey = inchiGenertor.getInchiKey();
            return Optional.of(inchiKey);
        } catch (InvalidSmilesException ise) {
            throw new InvalidStructureException("Nie udało sie sparsować SMILES = %s.".formatted(smiles));
        } catch (CDKException cdke) {
            throw new InvalidStructureException("Błąd wywołany przez CDKException przy próbie uzyskania ichiKey. Startowałem ze SMILES = %s. Wiadomość błędu: %s.".formatted(smiles, cdke.getMessage()));
        }
    }

    public static Optional<String> getInchi(String smiles) {
        SmilesParser smilesParser = new SmilesParser(DebugChemObjectBuilder.getInstance());
        try {
            IAtomContainer molecule = smilesParser.parseSmiles(smiles);
            if (!molecule.isEmpty()) {
                InChIGenerator inchiGenertor = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule);
                String inchi = inchiGenertor.getInchi();
                return Optional.of(inchi);
            } else {
                return Optional.empty();
            }

        } catch (InvalidSmilesException ise) {
            throw new InvalidStructureException("Nie udało sie sparsować SMILES = %s. Wiadomość błędu: %s.".formatted(smiles, ise.getMessage()));
        } catch (CDKException cdke) {
            throw new InvalidStructureException("Błąd wywołany przez CDKException przy próbie uzyskania inchi. Wiadomość błędu: %s.".formatted(cdke.getMessage()));
        }
    }

    public static Optional<InchiData> getInchiData(String smiles) {
        SmilesParser smilesParser = new SmilesParser(DebugChemObjectBuilder.getInstance());
        Optional<InchiData> returnedData = Optional.empty();
        try {
            IAtomContainer molecule =  smilesParser.parseSmiles(smiles);
            if (molecule.getAtomCount() > 0) {
                InChIGenerator inchiGenertor = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule);
                String inchi = inchiGenertor.getInchi();
                String inchiKey = inchiGenertor.getInchiKey();
                returnedData = Optional.of(new InchiData(inchi, inchiKey));
            }
        } catch (InvalidSmilesException ise) {
            throw new InvalidStructureException("Błąd przy parsowaniu SMILES = %s. Wiadomość błędu: %s".formatted(smiles, ise.getMessage()));
        } catch (CDKException cdke) {
            throw new InvalidStructureException("Błąd wywołany przez CDKException przy próbie uzyskania InChI dla SMILES = %s. Wiadomość błędu: %s.".formatted(smiles, cdke.getMessage()));
        } catch (Exception ex) {
            log.warn("Problematyczny SMILES: %s.".formatted(smiles));
            log.error("Nie udało się wygenerować InChi.", ex);
        }
        return returnedData;
    }
}
