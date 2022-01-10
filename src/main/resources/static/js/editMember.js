let file = document.querySelector('#file')
let nickname = document.querySelector('#nickname')
let edit_form = document.querySelector('#edit')

edit_form.addEventListener('submit', function(e) {
    e.preventDefault();
    const formData = new FormData();
    if (file.files[0] !== undefined) {
        formData.append('file', file.files[0])
    }
    formData.append('nickname', nickname.value)

    console.log('form data', formData)

    axios({
        method: 'put',
        url: '/member',
        data: formData
    }).then(function(res) {
        window.location.replace('/user')
    })
})