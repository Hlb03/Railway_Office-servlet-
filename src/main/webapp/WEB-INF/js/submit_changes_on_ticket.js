//<!--
//--------------------------------
// This code asks an admin whether he/she wants
// to confirm written changes to trip.
//--------------------------------
function submitChanges() {
    console.log("Come into JS submit");
    let confirmAction = confirm("This action will change data for this trip.\nAre you sure?");

    if (confirmAction) {
        console.log("Confirm this changes.");
        return true;
    } else {
        console.log("");
        return false;
    }
}
