package Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class SecurityService {

    private DBService databaseService;


    public SecurityService() throws SQLException, ClassNotFoundException {
        databaseService = new DBService();
    }

    public boolean isAuthorized(HttpServletRequest request) throws SQLException {
        String username = (String) request.getSession()
                .getAttribute("username");
        return (username != null && databaseService.containUser(username));
    }

    public boolean authenticate(String username, String password, HttpServletRequest request) throws SQLException {
        boolean isMatched = databaseService.authenticate(username,password);
        if (isMatched) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}