let sign_up_btn = document.querySelector('#sign-up')

let id = document.querySelector('#loginId')
let pw = document.querySelector('#password')
let nickname = document.querySelector('#nickname')

sign_up_btn.addEventListener('click', function() {
    axios.post('/signup', {
        loginId : id.value,
        password : pw.value,
        nickname : nickname.value
    })
    .then(function(res) {
        window.location.replace('/')
    })    
})
