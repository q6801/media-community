axios.get('/streaming-rooms')
    .then(function(res) {
        console.log(res.data)

        // boards
        let msgRooms = res.data
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
        let table_head_innertext = ['roomName', 'streamer']
        for(let i=0; i<td.length; i++) {
            td[i] = document.createElement('td')
            td[i].innerText = table_head_innertext[i]
            tr.appendChild(td[i])
        }
        thead.appendChild(tr)

        // 테이블 바디 제작
        for(idx in msgRooms) {
            let streamingRoom = msgRooms[idx]
            let tr = document.createElement('tr')
            tr.setAttribute('onClick', `location.href="/${streamingRoom.presenter}"`)
            let td = new Array(2);
            for(let i=0; i<td.length; i++) {
                td[i] = document.createElement('td')
                tr.appendChild(td[i])
            }
            td[0].innerText = streamingRoom.roomName
            td[1].innerText = streamingRoom.presenter
            tbody.appendChild(tr)
        }
                  
    })