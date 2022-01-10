//    <form method="post" action="/login" th:object="${member}">
//      <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
//
//      <div th:if="${#fields.hasGlobalErrors()}">
//        <p th:each="err : ${#fields.globalErrors}"
//          th:text="${err}"></p>
//      </div>
//
//      <div class="form-floating">
//        <input class="form-control" th:field="*{loginId}">
//        <label for="loginId">아이디</label>
//        <div th:errors="*{loginId}"></div>
//      </div>
//      <div class="form-floating">
//        <input type="password" class="form-control" th:field="*{password}" placeholder="Password">
//        <label for="password">비밀번호</label>
//        <div th:errors="*{password}"></div>
//      </div>
//      <button class="w-100 btn btn-lg btn-primary mt-1" type="submit">Sign in</button>
//    </form>

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
    })
})