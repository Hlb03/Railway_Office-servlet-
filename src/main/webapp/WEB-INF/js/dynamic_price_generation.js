//Function to generate total sum according to tickets amount
function showTotalPrice(input) {
    let html = 'Total price is: ';
    let money = '';

    for(let i=1; i<=input.value; i++) {
        // html = html +
        //     (document.getElementById('ticketAmount').value *
        //     document.getElementById('tripCost').value).toFixed(2);
        money = money + (document.getElementById('ticketAmount').value *
            document.getElementById('tripCost').value).toFixed(2);
    }

    const arraysOfMoney = money.split('.');

    for (let i = 0; i < arraysOfMoney.length; i++) {
        if (i === 0)
            html = html + arraysOfMoney[i] + ".";
        if (i === arraysOfMoney.length - 1)
            html = html + arraysOfMoney[i];
    }

    const element = document.getElementById('totalPrice');
    element.innerHTML = html;
}