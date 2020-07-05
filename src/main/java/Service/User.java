package Service;

import Service.DBService;
import java.sql.SQLException;

public class User{

    private String username;
    private String firstName;
    private String lastName;
    private String dob;

    private DBService databaseService;

    public User(String username) throws SQLException, ClassNotFoundException {
        this.username = username;
        this.databaseService = new DBService();
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}