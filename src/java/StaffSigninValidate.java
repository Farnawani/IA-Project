/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author farna
 */
@WebServlet(urlPatterns = {"/StaffSigninValidate"})
public class StaffSigninValidate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            response.setContentType("text/html");

            String email = request.getParameter("staffEmail");
            String pass = request.getParameter("staffPass");

            //connecting to database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM staffmembers WHERE StaffEmail = ? AND StaffPass = ?");
                stmt.setString(1, email);
                stmt.setString(2, pass);
                ResultSet user;
                user = stmt.executeQuery();

                if (user.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("staffEmail", email);
                    session.setAttribute("staffPass", pass);
                    RequestDispatcher rd = request.getRequestDispatcher("staffHome.jsp");
                    rd.forward(request, response);
                } else {
                    showMessageDialog(null, "Incorrect email or password!!");
                    response.sendRedirect("staffSignin.jsp");
                }

                out.close();
                user.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(StaffSigninValidate.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                out.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffSigninValidate.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
