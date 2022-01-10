let pathArr = window.location.pathname.split('/');
let board_id = pathArr[2]
console.log('pathArr : ' + pathArr)

// BOARD
axios.get('/boardInfo/' + board_id)
.then(function(res) {
    let board = res.data
    console.log('board : ' +  res.data)

    let boardInfo = document.querySelector('#board')

    let table = document.createElement('table');
    let tbody = document.createElement('tbody');
    table.setAttribute('class', "mt-5")
    table.appendChild(tbody)

    let tr0 = document.createElement('tr')
    let td_title = document.createElement('td')
    let td_title1 = document.createElement('h2')
    td_title.appendChild(td_title1)
    td_title1.innerText = board.title
    tr0.appendChild(td_title)

    let tr1 = document.createElement('tr')
    let td_writer = document.createElement('td')
    let td_views = document.createElement('td')
    let td_created = document.createElement('td')
    td_writer.innerText = board.writer
    td_views.innerText = '조회수 : ' + board.viewCnt
    td_created.innerText = board.createdAt

    tr1.appendChild(td_writer)
    tr1.appendChild(td_views)
    tr1.appendChild(td_created)

    tbody.appendChild(tr0)
    tbody.appendChild(tr1)
    boardInfo.appendChild(table)

    let hr = document.createElement('hr')
    hr.setAttribute('class', 'mt-3 mb-5')
    boardInfo.appendChild(hr)
    
    let content = document.createElement('div')
    content.setAttribute('class', "mt-5")
    content.innerHTML = board.content
    boardInfo.appendChild(content)

    axios.get('/memberInfo')
        .then(function(memberRes) {
            if (memberRes.data.username == board.username) {
                let edit = document.querySelector('#edit')
                let editBtn = document.createElement('button')
                edit.appendChild(editBtn)
           
                editBtn.setAttribute('onClick', `location.href="/editBoard/${board_id}"`)
                editBtn.setAttribute('class', 'btn btn-outline-secondary')
                editBtn.innerText = '글 수정'
            }
        })

})

// HEART
axios.get('/boardInfo/' + board_id + '/hearts')
    .then(function(res) {
        console.log(res.data)

        let hearts = res.data
        let heartInfo = document.querySelector('#hearts')
        let button = document.createElement('button')
        heartInfo.appendChild(button)

        if (hearts.pushed) {
            button.setAttribute('class', 'btn btn-outline-secondary active')
        } else {
            button.setAttribute('class', 'btn btn-outline-secondary')
        }
        button.innerText = '좋아요 수 : ' + hearts.heartsCnt

        button.addEventListener('click', function () {
            axios.put('/board/' + board_id + '/heart')
                .then(function(response) {
                    let pushedHeart = response.data
                    button.removeAttribute('class')

                    if (pushedHeart.pushed) {
                        button.setAttribute('class', 'btn btn-outline-secondary active')
                    } else {
                        button.setAttribute('class', 'btn btn-outline-secondary')
                    }
                    button.innerText = '좋아요 수 : ' + pushedHeart.heartsCnt
                })
        })

    })

// REPLY들
axios.get('/boardInfo/' + board_id + '/replies')
.then(function(res) {
    console.log(res.data)

    let replies = res.data
    let replyInfo = document.querySelector('#replies')
    let total_div = document.createElement('div')
    replyInfo.appendChild(total_div)
    
    for(idx in replies) {
        let reply = replies[idx]
        let container_div = document.createElement('div')
        let h4 = document.createElement('h5')
        h4.innerText = reply.writer

        let div1 = document.createElement('div')
        div1.innerText = reply.updatedAt

        container_div.appendChild(h4)
        container_div.appendChild(div1)
        container_div.setAttribute('class', 'mb-4')

        let content = document.createElement('div')
        content.innerText = reply.content

        hr = document.createElement('hr')
        hr.setAttribute('class', 'mt-3 mb-3')

        total_div.appendChild(container_div)
        total_div.appendChild(content)
        total_div.appendChild(hr)
    }
})

// reply form
let add_reply_form = document.querySelector('#add-reply')
let submit_reply = function(e) {
    e.preventDefault()
    
    let reply_content_dom = document.querySelector('#reply-content')
    let reply_content = reply_content_dom.value

    axios.post('/reply/'+ board_id, {
        content: reply_content
    }).then(function(res) {
        reply_content_dom.value = ''

        let reply = res.data

        let replyInfo = document.querySelector('#replies')
        let total_div = document.createElement('div')
        replyInfo.appendChild(total_div)
        
        let container_div = document.createElement('div')
        let h4 = document.createElement('h5')
        h4.innerText = reply.writer

        let div1 = document.createElement('div')
        div1.innerText = reply.updatedAt

        container_div.appendChild(h4)
        container_div.appendChild(div1)
        container_div.setAttribute('class', 'mb-4')

        let content = document.createElement('div')
        content.innerText = reply.content

        hr = document.createElement('hr')
        hr.setAttribute('class', 'mt-3 mb-3')

        total_div.appendChild(container_div)
        total_div.appendChild(content)
        total_div.appendChild(hr)

    })
}
add_reply_form.addEventListener('submit', submit_reply)

