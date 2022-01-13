

//var username = document.querySelector('#username').innerText;

axios.get('/memberInfo')
    .then(function(response) {
        let username = response.data.nickname
        let pathArr = window.location.pathname.split('/');
        let roomId = pathArr[pathArr.length - 1]

        console.log('username : ' + username);
        console.log('roomId : ' + roomId);

        var webSocket = new WebSocket("ws://dremean.com/ws/chat");
        var client = Stomp.over(webSocket);
        console.log(client);

        client.connect({}, function() {
            client.subscribe("/sub/chat/room/" + roomId, function (chat) {
                  var content = JSON.parse(chat.body);
        
                  var writer = content.writer;
        
                  let input = document.querySelector('#chats');
        
                  let span = document.createElement('span');
                  span.innerHTML = writer + ' : ' + content.message + ' </br>';
                  input.appendChild(span);
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

    })
    .catch(function(err) {
        console.log(err)
    });



