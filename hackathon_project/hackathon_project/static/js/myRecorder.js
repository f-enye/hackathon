$(document).ready(function(){

	var audio_context;
  	var recorder;

  	$("#recordButton").on("click", ToggleRecordButton);

  	// toggle button
    function ToggleRecordButton(evt) {
        var target = $(evt.target);

        if (target.hasClass("btn-default")) {
            target.addClass("btn-danger");
            target.removeClass("btn-default");
            startRecording();
        }
        else if (target.hasClass("btn-danger")) {
            target.addClass("btn-default");
            target.removeClass("btn-danger");
            stopRecording();
        }
    }

	function startUserMedia(stream) {
		var input = audio_context.createMediaStreamSource(stream);
		console.log('Media stream created.');

		// This is for debugging perposes
		//input.connect(audio_context.destination);
		console.log('Input connected to audio context destination.');

		var config = {};
        config["workerPath"] = location.origin + "/static/recorderJS/recorderWorker.js";

        recorder = new Recorder(input, config);

		//recorder = new Recorder(input);
		console.log('Recorder initialised.');
	}

	function startRecording(button) {
	    recorder && recorder.record();
	    console.log('Recording...');
	}

	function stopRecording(button) {
	    recorder && recorder.stop();
	    console.log('Stopped recording.');
	    
	    // create WAV download link using audio data blob
	    createDownloadLink();
	    
	    recorder.clear();
	}

	var formData = new FormData();

	function createDownloadLink() {
		recorder && recorder.exportWAV(function(blob) {
			var url = URL.createObjectURL(blob);
			var li = document.createElement('li');
			var au = document.createElement('audio');
			var hf = document.createElement('a');

			formData.append('file', blob)

			au.controls = true;
			au.src = url;
			hf.href = url;

			//hf.download = new Date().toISOString() + '.wav';
			//hf.innerHTML = hf.download;

			if( $("#recordingslist").children().length > 0 )
			{
				console.log('gah');
				$("#recordingslist").children().remove();
			}

			li.appendChild(au);
			li.appendChild(hf);
			recordingslist.appendChild(li);
		});
	}

	window.onload = function init() {
	    try {
	      // webkit shim
	      window.AudioContext = window.AudioContext || window.webkitAudioContext;
	      navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
	      window.URL = window.URL || window.webkitURL;
	      
	      audio_context = new AudioContext;
	      console.log('Audio context set up.');
	      console.log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
	    } catch (e) {
	      alert('No web audio support in this browser!');
	    }
	    
	    navigator.getUserMedia({audio: true}, startUserMedia, function(e) {
	      console.log('No live audio input: ' + e);
	    });
  };

	 $("#audioSubmitButton").on("click", function(evt){
	 	formData.append('csrf_token', $("#csrf_token").val())
	 	$.ajax({ 
	 		url: location.origin + "/submitAudioFile",
	 		type: "POST",
	 		data: formData,
	 		contentType: false,
	 		processData: false,
	 		success: function(data){
	 			console.log("hey");
	 			console.log(data);
	 		},
	 		error: function() {
          		alert("not so boa!");
        	}
	 	});
	 });

});

