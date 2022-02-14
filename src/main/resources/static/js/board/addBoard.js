add_board_form = document.querySelector('#add-board')
add_board_form.addEventListener('submit', post)

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
axios.get('board-category')
.then(function(res) {
    let categories = res.data.categories
    console.log(res)

    for (c in categories) {
          let option = document.createElement('option')
          option.setAttribute('value', categories[c])
          option.setAttribute('class', 'dropdown-item')
          option.innerText = categories[c]

          category.appendChild(option)
    }
})


function post(e) {
    e.preventDefault()
    let title = document.querySelector('#title')

    let title_val = title.value

    axios.post('/board',
            {
                'category': category.value,
                'title': title_val,
                'content': quill.root.innerHTML
            }
    ).then(function(res) {
        console.log(res.data)

        window.location.replace('/board/' + res.data.boardIdx)
    }).catch(function(err) {
        console.log(err);
    })
}


