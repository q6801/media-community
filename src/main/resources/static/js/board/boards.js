const params = new URLSearchParams(window.location.search)
let page = params.get('page')
if (page == null) {
    page = 1
}
let pathArr = window.location.pathname.split('/');
let category = pathArr[2]
let categoryDom = document.querySelector('#category')


axios.get('/boards/' + category + '?page=' + page)
.then(function(res) {
    console.log('page : ', page)
    console.log(res.data)

    let boards = res.data.boards
    let pagination = res.data.pagination

    // boards
    let boards_dom = document.querySelector('#boards')

    let table = document.createElement('table');
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody)
    table.setAttribute('class', "mt-5 table table-striped table-hover")
    boards_dom.appendChild(table)

    // 테이블 헤드 제작
    let tr = document.createElement('tr')
    let td = new Array(4);
    let table_head_innertext = ['title', 'writer', 'views', 'createdAt']
    for(let i=0; i<4; i++) {
        td[i] = document.createElement('td')
        td[i].innerText = table_head_innertext[i]
        tr.appendChild(td[i])
    }
    thead.appendChild(tr)

    // 테이블 바디 제작
    for(idx in boards) {
        let board = boards[idx]
        let tr = document.createElement('tr')
        let board_url = '/board/' + board.id
        tr.setAttribute('onClick', `location.href="${board_url}?page=${pagination.page}"`)
        let td = new Array(4);
        for(let i=0; i<4; i++) {
            td[i] = document.createElement('td')
            tr.appendChild(td[i])
        }
        td[0].innerHTML = board.title
        td[1].innerText = board.writer
        td[2].innerText = board.viewCnt
//        td[3].innerText = board.createdAt
        td[3].innerText = timeForToday(new Date(board.createdAt))
        tbody.appendChild(tr)
    }

    // pagination
    let div = document.createElement('div')
    if (pagination.prev) {
        let prev = document.createElement('button')
        prev.setAttribute('onClick', `location.href='/articles/community?page=${pagination.startPage-1}'`)
        prev.setAttribute('class', "btn btn-outline-secondary")
        prev.innerText = '이전'
        div.appendChild(prev)
    }
    for(let i=pagination.startPage; i<pagination.endPage+1; i++) {
        let btn = document.createElement('button')
        btn.innerText = i
        btn.setAttribute('onClick', `location.href='/articles/community?page=${i}'`)
        if (pagination.page == i) {
            btn.setAttribute('class', 'btn btn-primary')
        } else {
            btn.setAttribute('class', 'btn btn-secondary')
        }
        div.appendChild(btn)
    }
    if (pagination.next) {
        let next = document.createElement('button')
        next.setAttribute('onClick', `location.href='/articles/community?page=${pagination.endPage+1}'`)
        next.setAttribute('class', "btn btn-outline-secondary")
        next.innerText = '다음'
        div.appendChild(next)
    }
    boards_dom.appendChild(div)
})

axios.get('/board-category')
.then(function(res) {
    let categories = res.data.categories
    console.log(res)

    for (c in categories) {
        let li = document.createElement('li')
        let a = document.createElement('a')
        li.appendChild(a)
        li.setAttribute('class', 'nav-item')
        a.setAttribute('class', 'nav-link')
        a.setAttribute('href', '/articles/' + categories[c])
        a.innerText = categories[c]

        if (categories[c] === category) {
            a.setAttribute('class', 'nav-link active')
        }

        categoryDom.appendChild(li)
    }
})

function timeForToday(value) {
        const today = new Date();
        const timeValue = new Date(value);

        const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
        if (betweenTime < 1) return '방금전';
        if (betweenTime < 60) {
            return `${betweenTime}분전`;
        }

        const betweenTimeHour = Math.floor(betweenTime / 60);
        if (betweenTimeHour < 24) {
            return `${betweenTimeHour}시간전`;
        }

        const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
        if (betweenTimeDay < 365) {
            return `${betweenTimeDay}일전`;
        }

        return `${Math.floor(betweenTimeDay / 365)}년전`;
 }