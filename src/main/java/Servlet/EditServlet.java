package Servlet;

import Service.DBService;
import Service.SecurityService;
import Service.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EditServlet extends AbstractRoutableHttpServlet{


    private SecurityService securityService;

    private DBService databaseService;


    public EditServlet() throws SQLException, ClassNotFoundException {
        this.databaseService = new DBService();
    }

    @Override
    public String getMapping() {
        return "/edit";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean authorized = false;
        try {
            authorized = securityService.isAuthorized(req);
        } catch(SQLException ex) {
        }
        if(authorized){
            String username = (String)req.getSession().getAttribute("editing_user");
            try {
                User user = databaseService.getUser(username);
                req.setAttribute("username", user.getUsername());
                req.setAttribute("first_name", user.getFirstName());
                req.setAttribute("last_name", user.getLastName());
                req.setAttribute("dob",user.getDob());

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/edit.jsp");
            rd.include(req,resp);
        } else resp.sendRedirect("/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String)req.getSession().getAttribute("editing_user");
        if (req.getParameter("edit_username")!= null){
            String newUsername = req.getParameter("new_username");
            try {
                if (databaseService.containUser(newUsername)){
                    req.setAttribute("error","User already exist");
                }else {
                    databaseService.updateUsername(newUsername, username);
                    username = newUsername;
                }
                req.getSession().setAttribute("editing_user",username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (req.getParameter("edit_password") != null){
            String newPassword = req.getParameter("new_password");
            String confirmPassword = req.getParameter("confirm_password");
            if (newPassword.compareTo(confirmPassword) != 0){
                String error = "Password doesn't match";
                req.setAttribute("password_error", error);
            }else {
                try {
                    databaseService.updatePassword(newPassword, username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else if (req.getParameter("edit_first_name") != null){
            String newFirstname = req.getParameter("new_first_name");
            try {
                databaseService.updateFirstName(newFirstname,username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(req.getParameter("edit_last_name") != null){
            String newLastName = req.getParameter("new_last_name");
            try {
                databaseService.updateLastName(newLastName,username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(req.getParameter("edit_dob") != null){
            String newDob = req.getParameter("new_dob");
            try {
                databaseService.updateDob(newDob,username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(req.getParameter("back")!= null){
            resp.sendRedirect("/");
        }

        try {
            User user = databaseService.getUser(username);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String dob = user.getDob();
            req.setAttribute("username", username);
            req.setAttribute("first_name", firstName);
            req.setAttribute("last_name", lastName);
            req.setAttribute("dob",dob);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/edit.jsp");
        rd.include(req, resp);
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
