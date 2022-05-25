let sign_up_btn = document.querySelector('#sign-up')

let id = document.querySelector('#loginId')
let pw = document.querySelector('#password')
let pw_checker = document.querySelector('#passwordChecker')
let nickname = document.querySelector('#nickname')

sign_up_btn.addEventListener('click', function() {
    axios.post('/api/signup', {
        loginId : id.value,
        password : pw.value,
        passwordChecker : pw_checker.value,
        nickname : nickname.value
    })
    .then(function(res) {
        window.location.replace('/')
    }).catch(function(err) {
        pw.value = ''
        pw_checker.value = ''
        let error_dom = document.querySelector('#error')
        error_dom.innerText = err.response.data.error.errorMessage
    })
})
