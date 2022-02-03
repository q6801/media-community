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

window.onbeforeunload = function() {
	ws.close();
}

viewer_dom.addEventListener('click', function() {
    viewer()
})

let webSocket = new WebSocket('wss://' + location.host + '/ws/stream');
let client = Stomp.over(webSocket);

client.connect({}, function() {
//    client.subscribe("/sub/message/presenterResponse", function(message) {
//            let parsedMessage = JSON.parse(message.body);
//            presenterResponse(parsedMessage);
//        })
    client.subscribe("/sub/message/viewerResponse", function(message) {
        let parsedMessage = JSON.parse(message.body);
        viewerResponse(parsedMessage);
    })
    client.subscribe("/sub/message/iceCandidate/v", function(message) {
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


function viewer() {
	if (!webRtcPeer) {
//		showSpinner(video);
        videoWaiting(video);

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

