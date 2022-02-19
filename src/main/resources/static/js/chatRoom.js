

//var username = document.querySelector('#username').innerText;

let webSocket
let client
let pathArr = window.location.pathname.split('/');
let roomId = pathArr[pathArr.length - 1]
let username

window.onbeforeunload = function(e) {
	e.preventDefault();
	client.send("/pub/exit", {}, JSON.stringify({'roomId': roomId, writer: username}));
	webSocket.close();
}
const callChatRoom = async() => {
    try {
        const res = await axios.get('/chat-room/' + roomId)
        const res1 = await axios.get('/memberInfo')
        connect(res1)
    } catch(err) {
        console.log('err : ' +  err.response.data.errorCode)
        console.log('err : ' +  err.response.data.errorMessage)

        window.location.replace('/chat')
    }
}
callChatRoom()

const connect = function(response) {
        username = response.data.nickname
        pathArr = window.location.pathname.split('/');
        roomId = pathArr[pathArr.length - 1]

        webSocket = new WebSocket('wss://' + location.host + '/ws/chat');
        client = Stomp.over(webSocket);

        console.log('username : ' + username);
        console.log('roomId : ' + roomId);

        client.connect({}, function() {
            client.subscribe("/sub/chat/room/" + roomId, function (chat) {
                  var content = JSON.parse(chat.body);

                  var writer = content.writer;

                  let input = document.querySelector('#chats');

                  let span = document.createElement('span');
                  span.innerText = writer + ' : ' + content.message;

                  let br = document.createElement('br')
                  input.appendChild(span);
                  input.appendChild(br);

            });
            client.send("/pub/enter", {}, JSON.stringify({'roomId': roomId, 'writer': username}));
        });


        let chat_form = document.querySelector('#chat-form')
        chat_form.addEventListener('submit', function(e) {
            e.preventDefault()

            let msg = document.getElementById("chat-content");
            client.send("/pub/comm/message", {}, JSON.stringify({'roomId': roomId, message:msg.value, writer: username}));
            msg.value = ''
        })

    }
//axios.get('/memberInfo')
//    .then()
//    .catch(function(err) {
//        console.log(err)
//    });



