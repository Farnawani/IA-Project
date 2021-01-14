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
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author farna
 */
@WebServlet(urlPatterns = {"/StudentReserveSlot"})
public class StudentReserveSlot extends HttpServlet {

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

            String ID = request.getParameter("slotID");
            int slotID = Integer.parseInt(ID);
            String taID = request.getParameter("taID");
            String timeFrom = request.getParameter("timeFrom");
            HttpSession session = request.getSession();
            String studID = (String) session.getAttribute("studID");

           
            

            //connecting to database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("INSERT INTO appointments (StudID, StaffID, Date, OHID) VALUES(?, ?, ?, ?)");
                stmt.setString(1, studID);
                stmt.setString(2, taID);
                stmt.setString(3, timeFrom);
                stmt.setInt(4, slotID);

                stmt.executeUpdate();

                stmt = (PreparedStatement) con.prepareStatement("UPDATE officehours SET Avail = ? WHERE OHID = ?");
                stmt.setBoolean(1, false);
                stmt.setInt(2, slotID);
                stmt.executeUpdate();
                
                showMessageDialog(null, "Slot Reserved Successfully!!");
                String url = (String) session.getAttribute("origin");
                
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);


                out.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(StudentSignupValidate.class
                        .getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                out.close();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StudentSignupValidate.class
                        .getName()).log(Level.SEVERE, null, ex);
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
