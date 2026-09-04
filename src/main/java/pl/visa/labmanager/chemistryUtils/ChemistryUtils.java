package pl.visa.labmanager.chemistryUtils;

import org.openscience.cdk.debug.DebugChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.InvalidStructureException;

@Service
public class ChemistryUtils {
    private static final SmilesParser smilesParser = new SmilesParser(DebugChemObjectBuilder.getInstance());

    public static IAtomContainer parseSmiles(String smiles) {
        try {
            return smilesParser.parseSmiles(smiles);
        } catch (InvalidSmilesException e) {
            throw new InvalidStructureException("Nie udało sie sparsować SMILES = %s.".formatted(smiles));
        }

    }
}
