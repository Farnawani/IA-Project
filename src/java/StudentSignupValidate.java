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
@WebServlet(urlPatterns = {"/StudentSignupValidate"})
public class StudentSignupValidate extends HttpServlet {

    private static void sendEmail(String from, String pass, String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        
        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
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

            String name = request.getParameter("studName");
            String email = request.getParameter("studEmail");
            String pass = generatePass();
            String text = "Your password is " + pass + " , Please change it.";

            sendEmail("farnawanii@gmail.com", "f1a2r3n4a5wani", email, "Temporary Password", text);
            //connecting to database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM students WHERE StudEmail = ? AND StudPass = ?");
                stmt.setString(1, email);
                stmt.setString(2, pass);
                ResultSet user;
                user = stmt.executeQuery();

                if (user.next()) {
                    showMessageDialog(null, "Account Already Exists!!");
                } else {
                    stmt = (PreparedStatement) con.prepareStatement("INSERT INTO students (StudName, StudEmail, StudPass) VALUES(?, ?, ?)");
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, pass);

                    int result = stmt.executeUpdate();

                    if (result != 0) {
                        showMessageDialog(null, "Please Check Your Email for your Password!!");
                        RequestDispatcher rd = request.getRequestDispatcher("studentSignin.jsp");

                        rd.forward(request, response);
                    } else {
                        showMessageDialog(null, "Something went wrong!!");
                        RequestDispatcher rd = request.getRequestDispatcher("studentSignup.jsp");
                        rd.forward(request, response);
                    }
                }

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
