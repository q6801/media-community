const params = new URLSearchParams(window.location.search)
let page = params.get('page')
if (page == null) {
    page = 1
}

axios.get('/chat-rooms?page=' + page)
    .then(function(res) {
        console.log(res.data)
        let msgRooms = res.data.rooms
        let pagination = res.data.pagination

        // rooms
        let chatRooms_dom = document.querySelector('#chatRooms')

        let table = document.createElement('table');
        let thead = document.createElement('thead');
        let tbody = document.createElement('tbody');

        table.appendChild(thead);
        table.appendChild(tbody)
        table.setAttribute('class', "mt-5 table table-striped table-hover")
        chatRooms_dom.appendChild(table)

        // 테이블 헤드 제작
        let tr = document.createElement('tr')
        let td = new Array(2);
        let table_head_innertext = ['방 이름', '만든 사람']
        for(let i=0; i<td.length; i++) {
            td[i] = document.createElement('td')
            td[i].innerText = table_head_innertext[i]
            tr.appendChild(td[i])
        }
        thead.appendChild(tr)

        // 테이블 바디 제작
        for(idx in msgRooms) {
            let room = msgRooms[idx]
            let tr = document.createElement('tr')
            tr.setAttribute('onClick', `location.href="/chatRoom/${room.id}"`)
            let td = new Array(2);
            for(let i=0; i<td.length; i++) {
                td[i] = document.createElement('td')
                tr.appendChild(td[i])
            }
            td[0].innerHtml = room.roomName
            td[1].innerText = room.presenter
            tbody.appendChild(tr)
        }

        // pagination
            let div = document.createElement('div')
            if (pagination.prev) {
                let prev = document.createElement('button')
                prev.setAttribute('onClick', `location.href='/chat?page=${pagination.startPage-1}'`)
                prev.setAttribute('class', "btn btn-outline-secondary")
                prev.innerText = '이전'
                div.appendChild(prev)
            }
            for(let i=pagination.startPage; i<pagination.endPage+1; i++) {
                let btn = document.createElement('button')
                btn.innerText = i
                btn.setAttribute('onClick', `location.href='/chat?page=${i}'`)
                if (pagination.page == i) {
                    btn.setAttribute('class', 'btn btn-primary')
                } else {
                    btn.setAttribute('class', 'btn btn-secondary')
                }
                div.appendChild(btn)
            }
            if (pagination.next) {
                let next = document.createElement('button')
                next.setAttribute('onClick', `location.href='/chat?page=${pagination.endPage+1}'`)
                next.setAttribute('class', "btn btn-outline-secondary")
                next.innerText = '다음'
                div.appendChild(next)
            }
            chatRooms_dom.appendChild(div)
    })