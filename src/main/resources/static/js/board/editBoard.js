let pathArr = window.location.pathname.split('/');
let board_id = pathArr[2]
console.log('pathArr : ' + pathArr)


let editBtn = document.querySelector('#submit')
editBtn.addEventListener('click', post)

axios.get('/boardInfo/' + board_id)
.then(function(res) {
    console.log('board : ' + res.data)
    let board = res.data

    let title = document.querySelector('#title')
    let content = document.querySelector('#content')

    title.value = board.title
    content.value = board.content
})

function post() {
    let title = document.querySelector('#title')
    let content = document.querySelector('#content')

    let title_val = title.value
    let content_val = content.value

    axios.put('/board/' + board_id,
            {
                'title': title_val,
                'content': content_val
            }
    ).then(function(res) {
        console.log(res.data)
        window.location.replace('/board/' + board_id)
    }).catch(function(err) {
        console.log(err);
    })
}