add_board_form = document.querySelector('#add-board')
add_board_form.addEventListener('submit', post)

function post(e) {
    e.preventDefault()
    let title = document.querySelector('#title')
    let content = document.querySelector('#content')

    let title_val = title.value
    let content_val = content.value

    axios.post('/board',
            {
                'title': title_val,
                'content': content_val 
            }
    ).then(function(res) {
        console.log(res.data)

        window.location.replace('/board/' + res.data.boardIdx)
    }).catch(function(err) {
        console.log(err);
    })
}