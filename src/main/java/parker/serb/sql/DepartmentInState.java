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
public class DepartmentInState {
    
    public int id;
    public boolean active;
    public String code;
    public String description;
    
    /**
     * Load the list of party types by the current selected section
     * @return a list of all party types for the selected section
     */
    public static List loadAllDepartments() {
        List departmentsInStateList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from DeptInState order by code";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet departmentInStateRS = preparedStatement.executeQuery();
            
            while(departmentInStateRS.next()) {
                DepartmentInState department = new DepartmentInState();
                department.id = departmentInStateRS.getInt("id");
                department.active = departmentInStateRS.getBoolean("active");
                department.code = departmentInStateRS.getString("code");
                department.description = departmentInStateRS.getString("description");
                departmentsInStateList.add(department);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departmentsInStateList;
    }
}
