package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class SMDSLetter {
    
    public int id;
    public String relatedCaseYear;
    public String relatedCaseType;
    public String relatedCaseMonth;
    public String relatedCaseNumber;
    
    
    public static void addNewRelatedCase(String caseNumber) {
        Statement stmt = null;
        
        String[] parsedCaseNumber = caseNumber.split("-");
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO RelatedCase VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, parsedCaseNumber[0]);
            preparedStatement.setString(6, parsedCaseNumber[1]);
            preparedStatement.setString(7, parsedCaseNumber[2]);
            preparedStatement.setString(8, parsedCaseNumber[3]);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added " + caseNumber + " as Related Case", null);

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List loadDocumentNamesByTypeAndSection(String section, String type) {
        List<String> documentList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = ? AND"
                    + " type = ?"
                    + " order by cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet documentListRS = preparedStatement.executeQuery();
            
            while(documentListRS.next()) {
                documentList.add(documentListRS.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentList;
    }
    
    public static boolean checkCaseIsAlreadyRelated(String caseNumber) {
        String[] parsedCaseNumber = caseNumber.split("-");
        
        boolean newCase = true;
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select COUNT(*) AS Count from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " relatedCaseYear = ? AND"
                    + " relatedCaseType = ? AND"
                    + " relatedCaseMonth = ? AND"
                    + " relatedCaseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, parsedCaseNumber[0]);
            preparedStatement.setString(6, parsedCaseNumber[1]);
            preparedStatement.setString(7, parsedCaseNumber[2]);
            preparedStatement.setString(8, parsedCaseNumber[3]);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            if(relatedCaseRS.getInt("Count") > 0) {
                newCase = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newCase;
    }
}
