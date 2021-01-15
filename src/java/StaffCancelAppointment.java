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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author farna
 */
@WebServlet(urlPatterns = {"/StaffCancelAppointment"})
public class StaffCancelAppointment extends HttpServlet {

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

                String OHID = request.getParameter("OHID");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM appointments WHERE OHID = ?");
                stmt.setString(1, OHID);
                ResultSet set = stmt.executeQuery();
                String studentId = "";
                String date = "";
                if(set.next())
                {
                    studentId = set.getString("StudID");
                    date = set.getString("Date");
                    
                }
                
                stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM students WHERE StudID = ?");
                stmt.setString(1, studentId);
                set = stmt.executeQuery();
                String studentEmail = "";
                if(set.next())
                {
                    studentEmail = set.getString("StudEmail");
                }
                
                stmt = (PreparedStatement) con.prepareStatement("DELETE FROM appointments WHERE OHID = ?");
                stmt.setString(1, OHID);
                stmt.executeUpdate();
                
                stmt = (PreparedStatement) con.prepareStatement("UPDATE officehours SET Avail = 1 WHERE OHID = ?");
                stmt.setString(1, OHID);
                stmt.executeUpdate();

                showMessageDialog(null, "Appointment Cancelled Successfully!!");
                
//                sendEmail("farnawanii@gmail.com", "f1a2r3n4a5wani", studEmail, "Appointment Cancellation", text);

                String text = "Your Appointment, reserved at " + date + ", is cancelled.";
                sendEmail("farnawanii@gmail.com", "f1a2r3n4a5wani", studentEmail, "Appointment Cancellation", text);
                
                RequestDispatcher rd = request.getRequestDispatcher("staffViewAppointments.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                out.print(e);
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
