<%-- 
    Document   : studentSignin
    Created on : Jan 12, 2021, 4:16:54 PM
    Author     : farna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <form method="POST" action="StudentSigninValidate">
            <table border = "1">
                <tr>
                    <td>Email</td>
                    <td>
                        <input type="email" name="studEmail" required>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <input type="password" name="studPass" required>
                    </td>
                </tr>
            </table> 
            <input type="submit" value="Sign in">
        </form>
        <a href="studentSignup.jsp">Already Have an Account?</a>
    </body>
</html>
