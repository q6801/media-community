//const ws = new WebSocket('wss://' + location.host + '/helloworld');
var video;
var webRtcPeer;
let viewer_dom = document.querySelector('#viewer')
let connect_status = document.querySelector('#connect-status')
let pathArr = window.location.pathname.split('/');
let presenter_id = pathArr[1]

window.onload = function() {
	video = document.getElementById('localVideo');
}

window.onbeforeunload = function(e) {
	e.preventDefault();
	stop();
	webSocket.close();
}

viewer_dom.addEventListener('click', function() {
    viewer()
})

let webSocket = new WebSocket('wss://' + location.host + '/ws/stream');
let client = Stomp.over(webSocket);

client.connect({}, function() {
    client.subscribe("/user/sub/message/viewerResponse", function(message) {
        let parsedMessage = JSON.parse(message.body);
        viewerResponse(parsedMessage);
    })
    client.subscribe("/user/sub/message/iceCandidate", function(message) {
        let parsedMessage = JSON.parse(message.body);
        webRtcPeer.addIceCandidate(parsedMessage.candidate, function(error) {
            if (error) return console.error('Error adding candidate: ' + error);
        });
    })
    client.subscribe("/user/sub/message/stopCommunication", function(message) {
        connect_status.innerText = '방송 종료됨~'
        dispose();
    })
})


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


function viewer() {
	if (!webRtcPeer) {
		var options = {
			remoteVideo : video,
			onicecandidate : onIceCandidate
		}
		webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
			function(error) {
				if (error) {
					return console.error(error);
				}
				this.generateOffer(onOfferViewer);
		});
		console.log("peerConnection 생성~");
		webRtcPeer.peerConnection.onconnectionstatechange = function(ev) {
			switch(webRtcPeer.peerConnection.connectionState) {
			  case "new":
			  case "connecting":
				console.log("Connecting...");
				connect_status.innerText = '연결 시도중~'
				break;
			  case "connected":
				console.log("Online");
				connect_status.innerText = ''
				break;
			  case "disconnected":
				console.log("Disconnecting...");
				break;
			  case "closed":
				console.log("Offline");
				connect_status.innerText = '연결이 종료됨(?)'
				// 이건 presenter가 아닌 viewer의 문제라는 것
				// 연결이 끊겼을 때 다시 시도도 괜찮을듯
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

function onOfferViewer(error, offerSdp) {
	if (error)
		return console.error('Error generating the offer');
	console.info('Invoking SDP offer callback function ' + location.host);
	var message = {
		id : 'viewer',
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

