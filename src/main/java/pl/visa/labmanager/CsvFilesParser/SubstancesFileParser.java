package pl.visa.labmanager.CsvFilesParser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import pl.visa.labmanager.DbUtils.DbUtils;
import pl.visa.labmanager.substance.Substance;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SubstancesFileParser {


    public static void main(String[] args) {

         try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/SubstancesFileToParse.csv"))) {
            List<String[]> rows = reader.readAll();

            try{
                Connection con = DbUtils.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO substances (iupac_name, cas_number, uuid) VALUES (?, ?, UUID())");
                for (String[] row : rows.subList(1, rows.size())) {
                    String iupacName = row[3];
                    String cas = row[1];
                    ps.setString(1, iupacName);
                    ps.setString(2, cas);
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }





         } catch (IOException e) {
             System.out.println("Wystąpił błąd %s.".formatted(e.getMessage()));
             e.printStackTrace();
         } catch (CsvException e) {
             System.out.println("Wystąpił bła∂ przy parawaniu pliku csv.");
             e.printStackTrace();
         }
    }



}
