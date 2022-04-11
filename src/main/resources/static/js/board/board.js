let board_id = window.location.pathname.split('/')[2]

// BOARD
axios.get('/api/board/' + board_id)
.then(function(res) {
    let board = res.data
    console.log('board : ' +  res.data)

    let boardInfo = document.querySelector('#board')

    let title = document.createElement('h2')
    let colWriter = document.createElement('div')
    let colViews = document.createElement('div')
    let colCreated = document.createElement('div')

    colWriter.setAttribute('class', 'col')
    colViews.setAttribute('class', 'col')
    colCreated.setAttribute('class', 'col')

//    title.innerText = board.title
    title.innerHTML = board.title
    colWriter.innerText = '작성자 : ' + board.writer
    colViews.innerText = '조회수 : ' + board.viewCnt
    let tt = new Date(board.createdAt)
    colCreated.innerText = '작성일 : ' + tt.toLocaleString();

    boardInfo.appendChild(title)
    boardInfo.appendChild(colWriter)
    boardInfo.appendChild(colViews)
    boardInfo.appendChild(colCreated)

    let hr = document.createElement('hr')
    hr.setAttribute('class', 'mt-3 mb-5')
    boardInfo.appendChild(hr)
    
    let content = document.createElement('div')
    content.setAttribute('class', "mt-5")
    content.innerHTML = board.content
    boardInfo.appendChild(content)


    let edit = document.querySelector('#edit')
    let editBtn = document.createElement('button')
    edit.appendChild(editBtn)

    editBtn.setAttribute('onClick', `location.href="/editBoard/${board_id}"`)
    editBtn.setAttribute('class', 'btn btn-outline-secondary')
    editBtn.innerText = '글 수정'

    let deleteBtn = document.createElement('button')
    edit.appendChild(deleteBtn)

    deleteBtn.setAttribute('class', 'btn btn-outline-secondary')
    deleteBtn.innerText = '글 삭제'
    deleteBtn.addEventListener('click', function() {
        axios.delete('/api/board/' + board_id)
            .then(function() {
                window.location.replace('/articles/community')
            }).catch(function(err) {
                console.log('err : ' +  err.response.data.errorCode)
                console.log('err : ' +  err.response.data.errorMessage)
                alert(err.response.data.errorMessage)
            })
    })
})
.catch(function(err) {
    console.log('err : ' +  err.response.data.errorCode)
    console.log('err : ' +  err.response.data.errorMessage)
    window.location.replace('/articles/community')
})

// HEART
axios.get('/api/board/' + board_id + '/hearts')
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
        button.innerText = '좋아요 수 : ' + hearts.heartCnt

        button.addEventListener('click', function () {
            axios.put('/api/board/' + board_id + '/heart')
                .then(function(response) {
                    console.log('pushed : ' + response.data)
                    let pushedHeart = response.data
                    button.removeAttribute('class')

                    if (pushedHeart.pushed) {
                        button.setAttribute('class', 'btn btn-outline-secondary active')
                    } else {
                        button.setAttribute('class', 'btn btn-outline-secondary')
                    }
                    button.innerText = '좋아요 수 : ' + pushedHeart.heartCnt
                })
                .catch(function(err) {
                    console.log('err : ' +  err.response.data.errorCode)
                    console.log('err : ' +  err.response.data.errorMessage)

                    alert(err.response.data.errorMessage)
                })
        })

    })

// REPLY들
axios.get('/api/board/' + board_id + '/replies')
.then(function(res) {
    console.log(res.data)

    let replies = res.data
    let replyInfo = document.querySelector('#replies')
    let total_div = document.createElement('div')
    replyInfo.appendChild(total_div)

    axios.get('/api/member')
        .then(function(memberRes) {
            for(idx in replies) {
                let reply = replies[idx]
                let container_div = document.createElement('div')
                let h4 = document.createElement('h5')
                h4.innerText = reply.writer

                let div1 = document.createElement('div')
                let tt = new Date(reply.updatedAt)
                div1.innerText = tt.toLocaleString();

                container_div.appendChild(h4)
                container_div.appendChild(div1)
                container_div.setAttribute('class', 'mb-4')

                let content = document.createElement('div')
                content.innerText = reply.content

                hr = document.createElement('hr')
                hr.setAttribute('class', 'mt-3 mb-3')

                console.log('username', memberRes.data.nickname)
                console.log('writer', reply.writer)

                total_div.appendChild(container_div)
                if (memberRes.data.nickname == reply.writer) {
                    let button_deletion = document.createElement('button')
                    button_deletion.innerText = '삭제'
                    button_deletion.setAttribute('style', 'float:right;')
                    button_deletion.addEventListener('click', function() {
                        axios.delete("/api/reply/" + reply.id)
                        .then(function(res) {
                            window.location.reload()
                        })
                    })
                    total_div.appendChild(button_deletion)
                }
                total_div.appendChild(content)
                total_div.appendChild(hr)
            }
        }).catch(function(err) {
            for(idx in replies) {
                let reply = replies[idx]
                let container_div = document.createElement('div')
                let h4 = document.createElement('h5')
                h4.innerText = reply.writer

                let div1 = document.createElement('div')
                let tt = new Date(reply.updatedAt)
                div1.innerText = tt.toLocaleString();

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
})

// reply form
let add_reply_form = document.querySelector('#add-reply')
let submit_reply = function(e) {
    e.preventDefault()
    
    let reply_content_dom = document.querySelector('#reply-content')
    let reply_content = reply_content_dom.value

    let url = '/api/board/' + board_id + '/reply'

    axios.post(url, {
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

        let button_deletion = document.createElement('button')
        button_deletion.innerText = '삭제'
        button_deletion.setAttribute('style', 'float:right;')
        button_deletion.addEventListener('click', function() {
            axios.delete("/api/reply/" + reply.id)
            .then(function(res) {
                window.location.reload()
            })
        })

        total_div.appendChild(container_div)
        total_div.appendChild(button_deletion)
        total_div.appendChild(content)
        total_div.appendChild(hr)
    })
    .catch(function(err) {
        console.log('err : ' +  err.response.data.errorCode)
        console.log('err : ' +  err.response.data.errorMessage)

        alert(err.response.data.errorMessage)
    })
}
add_reply_form.addEventListener('submit', submit_reply)


