<%-- 
    Document   : staffMsgStud
    Created on : Jan 15, 2021, 4:58:42 PM
    Author     : farna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%String studID = request.getParameter("sid");%>
        <div>
            <form method="post" action="StaffSendMsg?studID=<%=studID%>">
                <textarea rows="3" cols="30" name="sendMsg"></textarea>
                <input type="submit" value="Send">
            </form>
        </div>
    </body>
</html>
