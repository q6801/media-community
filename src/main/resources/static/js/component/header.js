axios.get('/memberInfo')
    .then(function(response) {
        console.log('res : ' +  response)
        let user = document.querySelector('#user');
        let welcome = document.createElement('a')

        welcome.innerText = response.data.nickname + '님 환영합니다.'
        welcome.setAttribute('class', 'nav-link active')
        welcome.setAttribute('href', '/user')

        
        let logout_btn = document.createElement('a');

        logout_btn.setAttribute('href', '/logout')
        logout_btn.setAttribute('class', 'nav-link active')
        logout_btn.innerText = '로그아웃'

        user.appendChild(welcome)
        user.appendChild(logout_btn)
    })
    .catch(function(err) {
        console.log('err : ' +  err.response.data.errorCode)
        console.log('err : ' +  err.response.data.errorMessage)

        let user = document.querySelector('#user');
        let login_btn = document.createElement('a');

        login_btn.setAttribute('href', '/login')
        login_btn.setAttribute('class', 'nav-link active')
        login_btn.innerText = '로그인'


        user.appendChild(login_btn);
    });
