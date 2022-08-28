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
