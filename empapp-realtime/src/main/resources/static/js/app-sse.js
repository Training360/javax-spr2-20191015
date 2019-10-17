window.onload = function() {
    let evtSource = new EventSource("api/employees/messages");
    evtSource.addEventListener("message",
        function(event) {
            print(JSON.parse(event.data).content)
        });

}

function print(message) {
    let div = document.querySelector("#message-div");
    let p = document.createElement("p");
    p.innerHTML = message;
    div.appendChild(p);
}