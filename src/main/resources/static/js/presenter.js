//const ws = new WebSocket('wss://' + location.host + '/helloworld');
var video;
var webRtcPeer;

let presenter_dom = document.querySelector('#presenter')
let finish_streaming = document.querySelector('#finish-streaming')
let connect_status = document.querySelector('#connect-status')
let pathArr = window.location.pathname.split('/');
let presenter_id = pathArr[1]
console.log('pathArr : ' + pathArr)

window.onload = function() {
	video = document.getElementById('localVideo');
}

window.onbeforeunload = function(e) {
	e.preventDefault();
	axios.delete('/streaming-room').then(function(res) {
		connect_status.innerText = '방송 종료됨~'
	})
	stop();
	webSocket.close();
	// e.returnValue = ""
}

presenter_dom.addEventListener('click', function() {
    presenter()
})

let webSocket = new WebSocket('wss://' + location.host + '/ws/stream');
let client = Stomp.over(webSocket);

client.connect({}, function() {
    client.subscribe("/sub/message/presenterResponse", function(message) {
        let parsedMessage = JSON.parse(message.body);
        presenterResponse(parsedMessage);
    })
//    client.subscribe("/sub/message/viewerResponse", function(message) {
//            let parsedMessage = JSON.parse(message.body);
//            viewerResponse(parsedMessage);
//        })
    client.subscribe("/sub/message/iceCandidate/p", function(message) {
        let parsedMessage = JSON.parse(message.body);
        webRtcPeer.addIceCandidate(parsedMessage.candidate, function(error) {
            if (error) return console.error('Error adding candidate: ' + error);
        });
    })
    client.subscribe("/sub/message/stopCommunication", function(message) {
        let parsedMessage = JSON.parse(message.body);
        dispose();
    })
})

function presenterResponse(message) {
	if (message.response != 'accepted') {
		var errorMsg = message.message ? message.message : 'Unknown error';
		console.info('Call not accepted for the following reason: ' + errorMsg);
		dispose();
	} else {
		webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
			if (error)
				return console.error(error);
		});
	}
}

function viewerResponse(message) {
	if (message.response != 'accepted') {
		var errorMsg = message.message ? message.message : 'Unknow error';
		console.info('Call not accepted for the following reason: ' + errorMsg);
		dispose();
	} else {
		webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
			if (error)
				return console.error(error);
		});
	}
}


function presenter() {
	if (!webRtcPeer) {
		var options = {
			localVideo : video,
			onicecandidate : onIceCandidate
		}
		webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
				function(error) {
					if (error) {
						return console.error(error);
					}
					webRtcPeer.generateOffer(onOfferPresenter);
				});
        console.log('wrp')
		
		let roomName = document.querySelector('#room-name')
		webRtcPeer.peerConnection.onconnectionstatechange = function(ev) {
			switch(webRtcPeer.peerConnection.connectionState) {
			  case "new":
			  case "connecting":
				console.log("Connecting...");
				connect_status.innerText = '연결 시도중~'
				break;
			  case "connected":
				console.log("Online");
				axios.post('/streaming-room', {
					roomName: roomName.value
				}).then(function(res) {
					connect_status.innerText = '방 생성 완료~'
				})
				break;
			  case "disconnected":
				console.log("Disconnecting...");
				break;
			  case "closed":
				console.log("Offline");
				axios.delete('/streaming-room').then(function(res) {
					connect_status.innerText = '방송 종료됨~'
				})
				break;
			  case "failed":
				console.log("Error");
				connect_status.innerText = '연결 실패~'
				break;
			  default:
				console.log("Unknown");
				break;
			}
		}
	}
}

function onOfferPresenter(error, offerSdp) {
	if (error)
		return console.error('Error generating the offer');
	console.info('Invoking SDP offer callback function ' + location.host);
	var message = {
		id : 'presenter',
		sdpOffer : offerSdp
	}
	sendMessage(message);
}

function onIceCandidate(candidate) {
	var message = {
		id : 'onIceCandidate',
		candidate : candidate
	};
	sendMessage(message);
}

function stop() {
	var message = {
		id : 'stop'
	}
	sendMessage(message);
	dispose();
}

function dispose() {
	if (webRtcPeer) {
		webRtcPeer.dispose();
		webRtcPeer = null;
	}
}

function sendMessage(message) {
	var jsonMessage = JSON.stringify(message);
	client.send("/pub/streaming/" + message.id + "/" + presenter_id, {}, jsonMessage);
}

