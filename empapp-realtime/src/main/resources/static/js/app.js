window.onload = function() {
     let socket = new SockJS('/websocket-endpoint');
     let stompClient = Stomp.over(socket);
     stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/employees', function (message) {
            let content = JSON.parse(message.body).content;
            console.log(content);
            print(content);
        });
    });

    document.querySelector("#message-button").onclick = function() {
        let content = document.querySelector("#message-input").value;
        stompClient.send("/app/messages", {}, JSON.stringify({"content": content}));
    };
}

function print(message) {
    let div = document.querySelector("#message-div");
    let p = document.createElement("p");
    p.innerHTML = message;
    div.appendChild(p);
}