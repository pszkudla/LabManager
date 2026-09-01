package pl.visa.labmanager.substance;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.color.UniColor;
import org.openscience.cdk.smiles.SmilesParser;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SubstanceDrawer {
    private static SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());


    public static void main(String[] args) {
        drawMolecule("CC(=O)C", "acetone_test");
    }

    public static void drawMolecule(String smiles, String nameToSave) {

        Path writingPath = Paths.get("src/main/resources/static/images", nameToSave + ".png");

        try {
            IAtomContainer molecule = smilesParser.parseSmiles(smiles);
            StructureDiagramGenerator sdg = new StructureDiagramGenerator();
            sdg.setMolecule(molecule);

            sdg.generateCoordinates();
            IAtomContainer molecule2dRepresentation = sdg.getMolecule();

            new DepictionGenerator()
                    .withSize(500, 500)
                    .withFillToFit()
                    .withBackgroundColor(Color.WHITE)
                    .withAtomColors(new UniColor(Color.BLACK))
                    .depict(molecule2dRepresentation)
                    .writeTo("src/main/resources/static/images/" + nameToSave + ".png");


        } catch (InvalidSmilesException e) {
            System.out.println("Podany SMILES nie jest prawidłowy.");
            e.printStackTrace();
        } catch (CDKException cdke) {
            cdke.printStackTrace();
        } catch (IOException e) {
            System.out.println("Bład przy zapisywaniu obrazka.");
            e.printStackTrace();
        }

    }
}
