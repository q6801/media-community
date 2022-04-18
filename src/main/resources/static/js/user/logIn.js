
let id = document.querySelector('#loginId')
let pw = document.querySelector('#password')
let sign_form = document.querySelector('#sign-in')

sign_form.addEventListener('submit', function(e) {
    e.preventDefault()
    const formData = new FormData();
    formData.append('loginId', id.value)
    formData.append('password', pw.value)

    console.log('form data', formData)

    axios({
        method: 'post',
        url: '/login',
        data: formData
    }).then(function(res) {
        window.location.replace('/')
    }).catch(function(err) {
//        console.log('err : ' +  err.response.data.errorCode)
//        console.log('err : ' +  err.response.data.errorMessage)

        id.value = ''
        pw.value = ''

        let error_dom = document.querySelector('#error')
        error_dom.innerText = err.response.data.error.errorMessage

    })
})