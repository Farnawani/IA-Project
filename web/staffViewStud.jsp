<%-- 
    Document   : staffViewStud
    Created on : Jan 15, 2021, 7:57:19 PM
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
            String studName = request.getParameter("studName");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");
                
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM students WHERE StudName = ?");
                stmt.setString(1, studName);
                ResultSet query;
                query = stmt.executeQuery();
                
                if(query.next()){
                    String studName1 = query.getString("StudName");
                    String studEmail = query.getString("StudEmail");
        %>
        <table border="1">
            <tr>
                <td>Name:</td>
                <td>Email:</td>
            </tr>
            <tr>
                <td><%=studName1%></td>
                <td><%=studEmail%></td>
            </tr>
        </table>
        <%
                }
            } catch (Exception e) {
            }


        %>
    </body>
</html>
