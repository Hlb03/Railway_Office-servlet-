function ticketAlerts() {
    console.log("Come into JS validation")

    if (document.getElementById('ticketAmount').value * document.getElementById('tripCost').value
                > document.getElementById('userBalance').value){
        alert("Not enough balance.\n Please replenish it.");
        return false;
    }
    // else if(document.getElementById('ticketAmount').value > document.getElementById('tripSeats').value) {
    //     alert("Not enough tickets.\n Please select less or look for another trip.");
    //     return false;
    // }
    else {
        let confirmAction = confirm("Are you sure?");

        if (confirmAction) {
            console.log("Confirm deleting");
            return true;
        } else {
            console.log("Deleting canceled");
            return false;
        }

    }

}

