package Service;


import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;

public class DBService {

    enum user_table {
        username, password, firstname, lastname, dob;
    }

    private final String jdbcDriverStr;
    private final String jdbcURL;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public DBService() throws SQLException, ClassNotFoundException {
        this.jdbcDriverStr = "com.mysql.cj.jdbc.Driver";
        this.jdbcURL = "jdbc:mysql://localhost/oochw3webapp?"
                + "user=root&password=chakeera";
        Class.forName(jdbcDriverStr);
        connection = DriverManager.getConnection(jdbcURL);
        statement = connection.createStatement();
        createDatabase();
    }

    public void createDatabase() throws SQLException {
        statement.execute("create table if not exists user_table(username varchar(255) not null , password varchar(255) not null, firstname varchar(255), lastname varchar(200), DOB date)");
        resultSet = statement.executeQuery("select * from oochw3webapp.user_table;");
        if (!resultSet.next()){
            String hashed = BCrypt.hashpw("admin", BCrypt.gensalt());
            preparedStatement = connection.prepareStatement("insert into oochw3webapp.user_table values ('admin','"+ hashed +"','admin','admin','2020/07/05')");
            preparedStatement.execute();
        }
    }

    public void readData() throws Exception {
        try {
            resultSet = statement.executeQuery("select * from oochw3webapp.user_table;");
            getResultSet(resultSet);
        } finally {
            close();
        }
    }

    private void getResultSet(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            String username = resultSet.getString(user_table.username.toString());
            String password = resultSet.getString(user_table.password.toString());
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }
    }

    public boolean createUser(String username, String password) throws SQLException{
        String hashed = BCrypt.hashpw(password,BCrypt.gensalt());
        if (!containUser(username)){
            statement.execute("insert into user_table(username, password) values ( '" + username + "', '" + hashed + "')");
            return true;
        }
        return false;
    }
    public boolean containUser(String username) throws  SQLException{
        ArrayList<String> nameList = getAllUser();
        if (nameList.contains(username)){
            return true;
        }
        return false;
    }

    public boolean delUser(String username) throws SQLException{
        if (containUser(username)) {
            statement.execute("delete from user_table where username ='" + username + "';");
            return true;
        }
        return false;
    }

    public boolean authenticate(String username, String password) throws SQLException {
        if (containUser(username)) {
            resultSet = statement.executeQuery("select * from user_table where(username = '" + username +"')");
            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString(user_table.password.toString()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getAllUser() throws SQLException {
        resultSet = statement.executeQuery("select  * from user_table");
        ArrayList<String> nameList = new ArrayList<>();
        while (resultSet.next()){
            nameList.add(resultSet.getString(user_table.username.toString()));
        }
        return nameList;
    }

    public User getUser(String username) throws SQLException, ClassNotFoundException {
        User user = new User(username);
        resultSet = statement.executeQuery("select firstname from user_table where username = '" +  username +"';");
        resultSet.next();
        String firstname = resultSet.getString(user_table.firstname.toString());
        user.setFirstName(firstname);
        resultSet = statement.executeQuery("select lastname from user_table where username = '" +  username +"';");
        resultSet.next();
        String lastname = resultSet.getString(user_table.lastname.toString());
        user.setLastName(lastname);
        resultSet = statement.executeQuery("select dob from user_table where username = '" +  username +"';");
        resultSet.next();
        String dob = resultSet.getString(user_table.dob.toString());
        user.setDob(dob);

        return user;
    }

    public void updateUsername(String newUsername, String oldUsername) throws SQLException{
        statement.execute("update user_table set username = '"  + newUsername+ "' where username = '" + oldUsername+ "';");
    }

    public void updatePassword(String newPassword, String username)throws SQLException{
        String hased = BCrypt.hashpw(newPassword,BCrypt.gensalt());
        statement.execute("update user_table set password = '"  + hased + "' where username = '" + username+ "';");
    }

    public void updateFirstName(String newFirstName, String username)throws SQLException{
        statement.execute("update user_table set firstname = '"  + newFirstName+ "' where username = '" + username+ "';");
    }

    public void updateLastName(String newLastName, String username)throws SQLException{
        statement.execute("update user_table set lastname = '" + newLastName+ "' where username = '" + username+ "';");
    }

    public void updateDob(String newDob, String username)throws SQLException{
        statement.execute("update user_table set DOB = '" + newDob+ "' where username = '" + username+ "';");
    }
}