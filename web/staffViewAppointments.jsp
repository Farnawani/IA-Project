<%-- 
    Document   : staffViewAppointments
    Created on : Jan 15, 2021, 8:59:43 PM
    Author     : farna
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <button><a href="staffHome.jsp">Back</a></button>
        <br>
        <%
            String staffID = (String) session.getAttribute("staffID");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");
                
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM officehours WHERE staffID = ?");
                stmt.setString(1, staffID);
                ResultSet query;
                query = stmt.executeQuery();
        %>
        <h3>Office Hours</h3>
        <table border="1">   
            <tr>
                <td>From:</td>
                <td>To:</td>
            </tr>
        <%
                while(query.next()){
                    String from = query.getString("DateFrom");
                    String to = query.getString("DateTo");
                    String avail = query.getString("Avail");
                    String OHID = query.getString("OHID");
        %>
            <tr>
                <td><%=from%></td>
                <td><%=to%></td>
                <td style="text-align: center"><%= avail.equals("1") ? "-" : "Reserved"%></td>
                <td><a href="StaffDeleteSlot?OHID=<%=OHID%>">Delete Slot</a></td>
                <%= avail.equals("1") ? "<td></td>" : "<td><a href=\"StaffCancelAppointment?OHID=" + OHID + "\"> Cancel Appointment</a></td> "%>
            </tr>
            
        
        <%
                }%>
        </table>
            <%} catch (Exception e) {
                
            }


        %>
        
        <br>
        <h4>Add Slot</h4>
        <form method="POST" action="StaffAddSlot">  
            <input type="text"name="fromDate" placeholder="From">
            <input type="text" name="toDate" placeholder="To">
            <input type="submit" value="Add">
        </form>
    </body>
</html>
