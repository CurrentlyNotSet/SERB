package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class REPRecommendation {
    
    public int id;
    public String type;
    public String recommendation;
    
    
   
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadAllREPRecommendations() {
        List<REPRecommendation> recommendationList = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPRecommendation ORDER BY recommendation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                REPRecommendation act = new REPRecommendation();
                act.id = caseActivity.getInt("id");
                act.type = caseActivity.getString("type");
                act.recommendation = caseActivity.getString("recommendation");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return recommendationList;
    }
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
//    public static List loadAllActivity() {
//        List<ULPRecommendation> activityList = new ArrayList<ULPRecommendation>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName"
//                    + " from Activity"
//                    + " INNER JOIN Users"
//                    + " ON Activity.userID = Users.id"
//                    + " ORDER BY date DESC ";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//
//            ResultSet caseActivity = preparedStatement.executeQuery();
//            
//            while(caseActivity.next()) {
//                ULPRecommendation act = new ULPRecommendation();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.caseNumber = caseActivity.getString("caseNumber");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
}
