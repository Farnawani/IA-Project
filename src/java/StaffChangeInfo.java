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
@WebServlet(urlPatterns = {"/StaffChangeInfo"})
public class StaffChangeInfo extends HttpServlet {

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
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");
                
                String op = request.getParameter("op");
                String col = "", msg = "";
                
                HttpSession session = request.getSession();
                String email = (String) session.getAttribute("staffEmail");
                
                if (op.equals("1")) {
                    msg = "Name";
                    String var = request.getParameter("StaffName");
                    PreparedStatement stmt = (PreparedStatement) con.prepareStatement("UPDATE staffmembers SET StaffName = ? WHERE StaffEmail = ?");
                    stmt.setString(1, var);
                    stmt.setString(2, email);
                    stmt.executeUpdate();
                    session.setAttribute("staffName", var);
                    stmt.close();
                } else if (op.equals("2")) {
                    col = "StaffPass";
                    msg = "Password";
                    String var = request.getParameter("StaffPass");
                    PreparedStatement stmt = (PreparedStatement) con.prepareStatement("UPDATE staffmembers SET StaffPass = ? WHERE StaffEmail = ?");
                    stmt.setString(1, var);
                    stmt.setString(2, email);
                    stmt.executeUpdate();
                    session.setAttribute("staffPass", var);
                    stmt.close();
                }
                showMessageDialog(null, msg + " Changed Successfully!!");

                RequestDispatcher rd = request.getRequestDispatcher("staffHome.jsp");
                rd.forward(request, response);

                out.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffChangeInfo.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                out.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffChangeInfo.class.getName()).log(Level.SEVERE, null, ex);
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
