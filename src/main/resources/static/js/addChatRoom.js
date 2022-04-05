let room_form = document.querySelector('#room-form')

room_form.addEventListener('submit', function(e) {
    e.preventDefault()
    let roomName = document.querySelector('#roomName')

    axios.post('/api/chat-room', {
        roomName: roomName.value
    }).then(function(res) {
        window.location.replace('/chat')
    })
    .catch(function(err) {
        console.log(err)
        alert(err.response.data.errorMessage)
    })
})