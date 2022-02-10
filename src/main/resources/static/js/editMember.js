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
    }).catch(function(err) {
        console.log('err : ' +  err.response.data.errorCode)
        console.log('err : ' +  err.response.data.errorMessage)

        alert(err.response.data.errorMessage)
    })
})

let change_image = function(e) {
    pathpoint = file.value.lastIndexOf('.');
    filepoint = file.value.substring(pathpoint+1, file.length);
    filetype = filepoint.toLowerCase();

    if(filetype=='jpg'||filetype=='gif'||filetype=='png'||filetype=='jpeg'||filetype=='bmp') {
        if (file.files && file.files[0].size > (1 * 1024 * 1024)) {
            alert('파일 사이즈가 1mb를 넘습니다.');
            file.value = ''
            return false;
        }
        if(filetype=='bmp') {
            upload = confirm('BMP 파일은 웹상에서 사용하기엔 적절한 이미지 포맷이 아닙니다. 그래도 계속하시겠습니까?')
            if (!upload) return false;
        }
    } else {
        alert('이미지 파일만 선택할 수 있습니다.');
        file.value = ''
        return false;
    }

}

file.addEventListener('change', change_image)

