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

let category = document.querySelector('#category')
axios.get('/api/board-category')
.then(function(res) {
  let categories = res.data.response.categories
  console.log(res)

  for (c in categories) {
        let option = document.createElement('option')
        option.setAttribute('value', categories[c])
        option.setAttribute('class', 'dropdown-item')
        option.innerText = categories[c]

        category.appendChild(option)
  }
})


let editBtn = document.querySelector('#submit')
editBtn.addEventListener('click', post)

axios.get('/api/board/' + board_id)
.then(function(res) {
    let board = res.data.response

    let title = document.querySelector('#title')

    let category = document.querySelector('#category')
    let optionValues = category.options
    for(let j=0; j<optionValues.length; j++) {
        if (board.category==optionValues[j].value) {
            optionValues[j].selected=true;
        }
    }
    let anonymous = document.querySelector('#anonymous')
    anonymous.checked = board.anonymous
    title.value = board.title

    const delta = quill.clipboard.convert(board.content)
    quill.setContents(delta, 'silent')

})



function post() {
    let title = document.querySelector('#title')
    let title_val = title.value
    let anonymous = document.querySelector('#anonymous')

    axios.put('/api/board/' + board_id,
            {
                'category': category.value,
                'title': title_val,
                'content': quill.root.innerHTML,
                'anonymous': anonymous.checked
            }
    ).then(function(res) {
        console.log(res.data)
        window.location.replace('/board/' + board_id)
    }).catch(function(err) {
        console.log(err);
    })
}

