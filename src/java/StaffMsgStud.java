/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.jasper.Constants;

/**
 *
 * @author farna
 */
@WebServlet(urlPatterns = {"/StaffMsgStud"})
public class StaffMsgStud extends HttpServlet {

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
            Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                String studID = request.getParameter("studID");
                String text = request.getParameter("sendMsg");
                HttpSession session = request.getSession();
                String staffID = (String) session.getAttribute("studID");
                
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("INSERT INTO staffmessages (MessageText, SenderID, ReceiverID) VALUES(?, ?, ?)");
                stmt.setString(1, text);
                stmt.setString(2, staffID);
                stmt.setString(3, studID);
                stmt.executeUpdate();
        }catch (SQLException ex) {
//                Logger.getLogger(StudentSignupValidate.class
//                        .getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                out.close();

            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(StudentSignupValidate.class
//                        .getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
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
