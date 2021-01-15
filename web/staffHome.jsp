<%-- 
    Document   : StaffHome
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
        <title>Home</title>
    </head>
    <body>
        <form action="StaffSignoutValidate" method="POST">
            <input type="submit" value="Sign Out">
        </form>
        <div style="float: right">
            <form method="POST" action="staffViewStud.jsp"> 
                <input type="text" placeholder="Search by Name" name="studName">
                <input type="submit" value="Search">
            </form>
        </div>
            <button><a href="staffViewAppointments.jsp">View Appointments</a></button>
        <h1>Profile</h1>
        <%
            String staffName = (String) session.getAttribute("staffName");
            String staffEmail = (String) session.getAttribute("staffEmail");
            String staffID = (String) session.getAttribute("staffID");
        %>
        <table border="1">
            <tr>
                <td style="width: 30px">Name</td>
                <td style="width: 100px"><%=staffName%></td>
            </tr>
            <tr>
                <td style="width: 30px">Email</td>
                <td style="width: 100px"><%=staffEmail%></td>
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
            function msgReply() {
                document.getElementById("msg-reply").style.display = "block";
            }
        </script>
        <a onclick="changeName()">Change Name</a><br>
        <a onclick="changePass()">Change Password</a><br>
        <div id="change-name" style="display: none">
            <h6>Enter Name:</h6>
            <form method="post" action="StaffChangeInfo?op=1">
                <input type="text" name="StaffName">
                <input type="submit" value="Save">
            </form>
        </div>
        <div id="change-pass" style="display: none">
            <h6>Enter Password:</h6>
            <form method="post" action="StaffChangeInfo?op=2">
                <input type="text" name="StaffPass">
                <input type="submit" value="Save">
            </form>
        </div>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffManagement", "root", "root");

                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM messages WHERE ReceiverID = ?");
                stmt.setString(1, staffID);
                ResultSet query;
                query = stmt.executeQuery();
        %>
        <div style="float: right">
            <table border="1">
                <tr>
                    <td>Messages</td>
                </tr>
                <%
                    while (query.next()) {
                        String text = query.getString("MessageText");
                        String studID = query.getString("ReceiverID");
                %>
                <tr>
                    <td><%=text%></td>
                    <td><a onclick="msgReply()">Reply</a></td>
                    <td>
                        <div id="msg-reply" style="display: none">
                            <form method="post" action="StaffSendMsg?studID=<%=studID%>">
                                <textarea rows="3" cols="30" name="sendMsg"></textarea>
                                <input type="submit" value="Send">
                            </form>
                        </div>
                    </td>
                </tr>
                <%}%>
            </table>
        </div>
        <%stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM students");
                query = stmt.executeQuery();%>
        <div>
            <form action="staffMsgStud.jsp" method="POST">
                <label for="studs">Select a Student:</label>
                <select name="sid" id="studs">
                    <%
                        while (query.next()) {
                            String sID = query.getString("StudID");
                            String sName = query.getString("StudName");
                    %>
                    <option value="<%=sID%>"><%=sName%></option>
                    <%
                        }
                    %>
                </select>
                <br><br>
                <input type="submit" value="Select">
            </form>
        </div>
        <%
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
