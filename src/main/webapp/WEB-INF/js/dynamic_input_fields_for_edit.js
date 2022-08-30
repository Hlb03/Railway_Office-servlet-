
//Function to generate new input fields when a new trip is creating

function newFields(input) {
    let html = '';

    for(let i=1; i<=input.value; i++) {
        html = html +
            '<br><select class="survey_options" style="width: 275px; height: 30px" name="settlement' + i + '">' +
            '<c:forEach items="${requestScope.allSettlements}" var="set">' +
            '<option value="${set.getId()}">${set.getName()}</option>' +
            '</c:forEach> </select>';
    }

    const element = document.getElementById('elements');
    element.innerHTML = html;
}