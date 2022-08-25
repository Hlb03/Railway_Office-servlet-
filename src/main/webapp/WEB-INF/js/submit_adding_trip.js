//<!--
//--------------------------------
// This code asks an admin whether he/she wants
// to delete all outdated trips.
//--------------------------------
function submitAddingTrip() {
    console.log("Come into JS submit");
    let confirmAction = confirm("This action will create new trip with given parameters.\nAre you sure?");

    if (confirmAction) {
        console.log("Confirm deleting");
        return true;
    } else {
        console.log("Deleting canceled");
        return false;
    }
}
