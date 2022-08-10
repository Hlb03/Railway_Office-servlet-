// <script type="text/javascript" > //language="JavaScript"
//<!--
//--------------------------------
// This code compares two fields in a form and submit it
// if they're the same, or not if they're different.
//--------------------------------
function checkPassword(theForm) {
    console.log("Come into JS validation")

    if (theForm.password.value !== theForm.submitPassword.value) {
        alert("Passwords are not the same");
        console.log("Different passwords here")
        return false;
    } else {
        return true;
    }
}
// </script>