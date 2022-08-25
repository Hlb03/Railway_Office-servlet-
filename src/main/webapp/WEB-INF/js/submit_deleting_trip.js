//<!--
//--------------------------------
// This code asks an admin whether he/she wants
// to delete all outdated trips.
//--------------------------------
function submitDeletingTrip() {
    console.log("Come into JS submit");
    let confirmAction = confirm("This action will delete current trip.\nAre you sure?");

    if (confirmAction) {
        console.log("Confirm deleting");
        return true;
    } else {
        console.log("Deleting canceled");
        return false;
    }
}
