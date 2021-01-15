<%-- 
    Document   : StudentHome
    Created on : Jan 11, 2021, 4:33:18 PM
    Author     : farna
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home here</title>
    </head>
    <body>
        <form action="StudentSignoutValidate" method="POST">
            <input type="submit" value="Sign Out">
        </form>
        <h1>Profile</h1>
        <%
            String name = (String) session.getAttribute("studName");
            String email = (String) session.getAttribute("studEmail");
            String id = (String) session.getAttribute("studID");
        %>
        <table border="1">
            <tr>
                <td style="width: 30px">Name</td>
                <td style="width: 100px"><%=name%></td>
            </tr>
            <tr>
                <td style="width: 30px">Email</td>
                <td style="width: 100px"><%=email%></td>
            </tr>
        </table>
        <br>
        <script>
            function changeName() {
                document.getElementById("change-name").style.display = "block";
            }
            function changePass() {
                document.getElementById("change-pass").style.display = "block";
            }
        </script>
        <a onclick="changeName()">Change Name</a><br>
        <a onclick="changePass()">Change Password</a><br>
        <div id="change-name" style="display: none">
            <h6>Enter Name:</h6>
            <form method="post" action="StudentChangeInfo?op=1">
                <input type="text" name="StudName">
                <input type="submit" value="Save">
            </form>
        </div>
        <div id="change-pass" style="display: none">
            <h6>Enter Password:</h6>
            <form method="post" action="StudentChangeInfo?op=2">
                <input type="text" name="StudPass">
                <input type="submit" value="Save">
            </form>
        </div>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT subjects.SubName, staffmembers.StaffName, staffmembers.StaffID"
                        + " FROM subjects"
                        + " Left Join staffmembers on subjects.StaffID = staffmembers.StaffID"
                        + " order by subjects.SubName;");
                ResultSet query;
                query = stmt.executeQuery();
        %>
        <div>
            <h3>List of Subjects</h3>
            <table border="3">
                <tr style="font-weight: bold">
                    <td style="width: auto">Subject Name</td>
                    <td style="width: auto">TA Name</td>
                </tr>
                <%
                    while (query.next()) {
                        String subName = query.getString("SubName");
                        String taName = query.getString("StaffName");
                        String taID = query.getString("StaffID");
                %>
                <tr>
                    <td><%=subName%></td>
                    <td><a href="viewStaff.jsp?taID=<%=taID%>&taName=<%=taName%>"><%=taName%></a></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
            <%
            stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM staffmessages WHERE ReceiverID = ?");
            stmt.setString(1, id);

            query = stmt.executeQuery();

        %>
        <br>
        <div>
            <table border="1">
                <tr>
                    <td>Messages:</td>
                </tr>
                <%                    while (query.next()) {
                        String text = query.getString("MessageText");
                %>
                <tr>
                    <td><%=text%></td>
                </tr>
                <%}%>
            </table>
        </div>
        <%
            stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM appointments WHERE StudID = ?");
            stmt.setString(1, id);
            query = stmt.executeQuery();

            if (query.next()) {
                String time = query.getString("Date");
                String appID = query.getString("AppointID");
                String OHID = query.getString("OHID");
        %>
        <div>
            My Appointments<br>
            <table border="1">
                <tr>
                    <td>Time</td>
                </tr>
                <tr>
                    <td><%=time%></td>
                  <td><a href="StudentDeleteAppointment?appID=<%=appID%>&slotID=<%=OHID%>&slotTime=<%=time%>">delete</a></td>
                </tr>
                <%
                    while (query.next()) {
                        time = query.getString("Date");
                        appID = query.getString("AppointID");
                        OHID = query.getString("OHID");
                %>
                <tr>
                    <td><%=time%></td>
                    <td><a href="StudentDeleteAppointment?appID=<%=appID%>&slotID=<%=OHID%>&slotTime=<%=time%>">delete</a></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        
        <%

                }
                out.close();
                query.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
//                Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
//                // RequestDispatcher rd=request.getRequestDispatcher("Login.html");  
//                //rd.forward(request, response);
//                out.close();
            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
            }
        %>
    </body>
</html>
