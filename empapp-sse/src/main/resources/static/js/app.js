window.onload = function() {
    let evtSource = new EventSource("api/numbers");
    evtSource.addEventListener("number",
        function(event) {
            print(event.data)
        });

}

function print(message) {
    let div = document.querySelector("#message-div");
    let p = document.createElement("p");
    p.innerHTML = message;
    div.appendChild(p);
}