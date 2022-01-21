
// <div class="card" style="width: 18rem;">
//   <img src="..." class="card-img-top" alt="...">
//   <div class="card-body">
//     <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
//   </div>
// </div>


axios.get('/memberInfo')
    .then(function(res) {
        console.log('user info', res.data)
        let memberInfo = res.data
        let user_dom = document.querySelector('#user-info')
        
        let card = document.createElement('div')
        card.setAttribute('class', 'card')
        card.setAttribute('style', 'width: 18rem')

        let card_body = document.createElement('div')
        card_body.setAttribute('class', 'card-body')
        
        let card_text0 = document.createElement('p')
        let card_text1 = document.createElement('p')

        card_text0.innerText = 'id : ' + memberInfo.loginId
        card_text1.innerText = 'nickname : ' + memberInfo.nickname

        card_body.appendChild(card_text0)
        card_body.appendChild(card_text1)


        if (memberInfo.email !== null) {
            console.log(memberInfo.email)
            let card_text2 = document.createElement('p')
            card_text2.innerText = 'email : ' + memberInfo.email
            card_body.appendChild(card_text2)
        } else {
            let card_text2 = document.createElement('p')
            let email_input = document.createElement('input')
            let btn = document.createElement('button')
            
            btn.innerText = '인증'
            card_text2.innerText = '이메일 인증하기'
            email_input.setAttribute('type', 'email')
            card_body.appendChild(card_text2)
            card_body.appendChild(email_input)
            card_body.appendChild(btn)

            btn.addEventListener('click', function() {
                axios.post('/email', {
                    email: email_input.value
                }).then(function(res) {
                    window.location.replace('/confirm-email')
                })
            })
        }

        let sign_out_div = document.createElement('div')
        let sign_out_text = document.createElement('div')
        sign_out_text.innerText = '회원 탈퇴를 원할 시 sign out을 써주세요'
        let sign_out_input = document.createElement('input')
        sign_out_input.setAttribute('placeholder', 'sign out')
        let sign_out = document.createElement('button')
        sign_out.innerText = '회원 탈퇴'

        sign_out_div.appendChild(sign_out_text)
        sign_out_div.appendChild(sign_out_input)
        sign_out_div.appendChild(sign_out)
        

        sign_out.addEventListener('click', function() {
            if (sign_out_input.value === 'sign out') {
                axios.delete('/member')
                    .then(function(res) {
                        window.location.replace('/')
                    })
            }
        })


        let img = document.createElement('img')
        img.setAttribute('src', memberInfo.imageUrl)
        img.setAttribute('class', 'card-img-top')


        user_dom.appendChild(card)
        card.appendChild(img)
        card.appendChild(card_body)

        
        user_dom.appendChild(sign_out_div)

    })