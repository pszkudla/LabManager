package pl.visa.labmanager.substance;

import org.hibernate.annotations.processing.SQL;
import pl.visa.labmanager.DbUtils.DbUtils;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubstanceUtils {

    public static NameToStructure nms = NameToStructure.getInstance();

    public static void main(String[] args) {

    }

    public static void createImage(String substanceUuid, String smiles) {

    }

    public static void fillDatabaseWithSmiles() {
        try {
            Connection con = DbUtils.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM substances WHERE smiles IS NULL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String substranceName = rs.getString("iupac_name");
                Long id = rs.getLong("id");
                getAndUpdateSmilesFromName(substranceName, con, id);
            }
        }
        catch (SQLException e) {
            System.out.println("Błąd przy tworzeniu połączenia.");
            e.printStackTrace();
        }
    }

    public static void getAndUpdateSmilesFromName(String name, Connection con, Long id) {
        String smiles = nms.parseToSmiles(name);
        if (smiles != null) {
            try  {
                PreparedStatement ps = con.prepareStatement("UPDATE substances SET smiles = ? WHERE id = ?");
                ps.setString(1, smiles);
                ps.setLong(2, id);
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Nie udało się zaktualizować wiersza.");
                e.printStackTrace();
            }
        }
    }
}
