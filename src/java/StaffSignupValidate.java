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
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.sql.SQLException;
import java.util.Random;
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
@WebServlet(urlPatterns = {"/StaffSignupValidate"})
public class StaffSignupValidate extends HttpServlet {

    void sendAnEmail() {
        String from = "farnawanii@gmail.com";
        String to = "farnawani@outlook.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "f1a2r3n4a5wani");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Bingo");
            message.setText("Done MotherFucker!!");

            // Send message  
            Transport.send(message);
            System.out.println("message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    String generatePass() {
        String pass = "";
        String alph = "qwertyuiopasdfghjklzxcvbnm1234567890";
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int r = random.nextInt(alph.length());
            pass += alph.charAt(r);
        }
        return pass;
    }

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

            String name = request.getParameter("staffName");
            String email = request.getParameter("staffEmail");
            String pass = generatePass();

            sendAnEmail();
            //connecting to database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("INSERT INTO staffmembers (StaffName, StaffEmail, StaffPass) VALUES(?, ?, ?)");
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, pass);

                int result = stmt.executeUpdate();

                if (result != 0) {
//                    HttpSession session = request.getSession();
//                    session.setAttribute("customerID", id);
//                    session.setAttribute("customerPass", pass);
                    RequestDispatcher rd = request.getRequestDispatcher("staffHome.jsp");

                    rd.forward(request, response);
                } else {
                    showMessageDialog(null, "Incorrect username or password !!");
                    RequestDispatcher rd = request.getRequestDispatcher("staffSignup.jsp");
                    rd.forward(request, response);
                }

                out.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(StaffSignupValidate.class
                        .getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                // RequestDispatcher rd=request.getRequestDispatcher("Login.html");  
                //rd.forward(request, response);
                out.close();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffSignupValidate.class
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
