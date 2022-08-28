
//Function to generate new input fields when a new trip is creating

function getNewStationsInput(input) {
    let html = '';

    for(let i=1; i<=input.value; i++) {
        html = html + '<tr>' +
                      '<td><label>Station number:' + i + '</label></td>' +
                      '<td> <select style="width: 170px" name="settlement' + i + '">' +
                      '<c:forEach items="${requestScope.allSettlements}" var="set">' +
                      '<option value="${set.getId()}">${set.getName()}</option>' +
                      '</c:forEach> </select>' +
                      '</td>' +
                      '</tr>';
    }

    const element = document.getElementById('settlementAmount');
    element.innerHTML = html;
}