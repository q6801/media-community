let pathArr = window.location.pathname.split('/');
let board_id = pathArr[2]
console.log('pathArr : ' + pathArr)

hljs.configure({   // optionally configure hljs
  languages: ['javascript', 'java', 'python', 'c++']
});

let quill = new Quill('#editor', {
    modules: {
        syntax: true,
        toolbar: [
              [{ header: [1, 2, false] }],
              ['bold', 'italic', 'underline'],
              ['code-block']
            ]
    },
    theme: 'snow'
  });
let editBtn = document.querySelector('#submit')
editBtn.addEventListener('click', post)

axios.get('/boardInfo/' + board_id)
.then(function(res) {
    console.log('board : ' + res.data)
    let board = res.data

    let title = document.querySelector('#title')
//    let content = document.querySelector('#content')

    title.value = board.title

    const delta = quill.clipboard.convert(board.content)
    quill.setContents(delta, 'silent')

})

function post() {
    let title = document.querySelector('#title')

    let title_val = title.value

    axios.put('/board/' + board_id,
            {
                'title': title_val,
                'content': quill.root.innerHTML
            }
    ).then(function(res) {
        console.log(res.data)
        window.location.replace('/board/' + board_id)
    }).catch(function(err) {
        console.log(err);
    })
}