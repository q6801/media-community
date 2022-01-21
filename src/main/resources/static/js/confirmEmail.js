function numberMaxLength(e){
    if(e.value.length > e.maxLength){
        e.value = e.value.slice(0, e.maxLength);
    }
}

let email_confirm_form = document.querySelector('#email-confirm')

email_confirm_form.addEventListener('submit', function(e) {
    e.preventDefault()
    let verifying_num_input = document.querySelector('#verifying-num')
    let verifying_num = verifying_num_input.value

    axios.post('/confirm-email', {
        verifyingNum: verifying_num
    }).then(function(res) {
        window.location.replace('/user')
    }).catch(function(err) {
        console.log('err : ' +  err.response.data.errorCode)
        console.log('err : ' +  err.response.data.errorMessage)

        verifying_num_input.value = ''

        let error_dom = document.querySelector('#error')
        error_dom.innerText = err.response.data.errorMessage
    })
})