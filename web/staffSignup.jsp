<!DOCTYPE html>
<!--
Name: Abdelrahman Mohamed Elfarnawani
ID: 20160334
Group: CS_IS_3
-->




<html>
    <head>
        <title>Sign Up</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
  
</head>
    <body>
        <form method="POST" onsubmit="return submitForm();" action="StaffSignupValidate">
            <table border = "1">
                <tr>
                    <td>Name</td>
                    <td>
                        <input type="text" name="staffName" required>
                    </td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>
                        <input type="email" name="staffEmail" required>
                    </td>
                </tr>
            </table> 
                       <div id="my_captcha_form" class="g-recaptcha" data-sitekey="6LeThSYaAAAAAAuePbTw5Opr4F4mYz6LIG_597xU"></div>
                        <div id="g-recaptcha-error"></div>
                   <input type="submit" value="Sign up">
        </form>
    </body>
    
<script>
 function submitForm() {
    var response = grecaptcha.getResponse();
    
    if(response.length === 0) {
        document.getElementById('g-recaptcha-error').innerHTML = '<span style="color:red;">Please verify that you are human.</span>';
        return false;
    }
    return true;
}
 
function verifyCaptcha() {
    document.getElementById('g-recaptcha-error').innerHTML = '';
}
</script>
</html>