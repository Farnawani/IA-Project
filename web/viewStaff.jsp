<%-- 
    Document   : viewStaff
    Created on : Jan 14, 2021, 2:24:54 AM
    Author     : farna
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
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
        <button><a href="studentHome.jsp">Back to Home</a></button>
        <%
            String taID = request.getParameter("taID");
            String taName = request.getParameter("taName");
        %>
        <h1><%=taName%></h1><br>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                String var = request.getParameter("StudName");
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM officehours WHERE StaffID = ?");
                stmt.setString(1, taID);
                ResultSet hours;
                hours = stmt.executeQuery();
        %>
        Office Hours:
        <table border="1">
            <tr style="font-weight: bold">
                <td style="width: 30px">From</td>
                <td style="width: 30px">To</td>
                <td>Availability</td>
            </tr>
            <%
                 session.setAttribute("origin", "viewStaff.jsp?" +  request.getQueryString());
                while (hours.next()) {
                    String timeID = hours.getString("OHID");
                    String timeFrom = hours.getString("DateFrom");
                    String timeTo = hours.getString("DateTo");
                    Boolean avail = hours.getBoolean("Avail");

            %>
            <tr>
                <td><%=timeFrom%></td>
                <td><%=timeTo%></td>
                <%
                    if (avail) {
                        
                %>
                <td><a href="StudentReserveSlot?slotID=<%=timeID%>&taID=<%=taID%>&timeFrom=<%=timeFrom%>">Reserve Slot</a></td>
                <%
                } else {
                %>
                <td>Slot Reserved</td>
                <%
                    }
                %>
            </tr>
            <%
                }
                String studID = (String)session.getAttribute("studID");
            %>
        </table>
        <div>
            Send Message
            <form method="POST" action="StudentSendMsg?studID=<%=studID%>&taID=<%=taID%>">
                <textarea rows="5" cols="50" name="sendMsg"></textarea>
                <input type="submit" value="Send">
            </form>
        </div>
        <%
              
                out.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
//                Logger.getLogger(StudentChangeInfo.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                out.close();
            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(StudentChangeInfo.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
            }
        %>
    </body>
</html>
