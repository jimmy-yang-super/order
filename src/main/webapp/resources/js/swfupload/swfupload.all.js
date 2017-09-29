//document.write('<script type="text/javascript" src="/js/swfupload/swfupload.js"></script>');
//document.write('<script type="text/javascript" src="/js/swfupload/swfupload.queue.js"></script>');
//document.write('<script type="text/javascript" src="/js/swfupload/fileprogress.js"></script>');
//document.write('<script type="text/javascript" src="/js/swfupload/handlers.js"></script>');
var upload = {
	init: function(options){
		
		var fls=flashChecker();
		if(fls.f){
//			console.log(111);
		}else{
//			console.log(222);
		}
		
		var settings = {
			flash_url : "/js/fileupload/swfupload.swf",
			upload_url: options.upload_url,
			file_post_name : options.file_post_name || "filename",
			post_params: options.params || {},
			file_size_limit : options.file_size_limit || "100 MB",
			file_types : options.file_types || "*.jpg;*.gif;*.png;*.bmp",
			file_types_description : options.file_types_description || "文件类型为图片",
			file_upload_limit : options.file_upload_limit || 100, //上传次数
			file_queue_limit : options.file_queue_limit || 0, //上传张数限制，0为不限制
			custom_settings : options.custom_settings || {progressTarget : "", cancelButtonId : ""},
			debug: false,

			// Button settings
			button_placeholder_id: options.button_placeholder_id,
			button_width: options.button_width || "65",
			button_height: options.button_height || "29",
			button_image_url: options.button_image_url || "",
			button_text: options.button_text || "",
			button_text_style: options.button_text_style || ".theFont { font-size: 16; }",
			button_text_left_padding: options.button_text_left_padding || 12,
			button_text_top_padding: options.button_text_top_padding || 3,
			
			button_window_mode:"transparent",
			
			// The event handler functions are defined in handlers.js
			file_queued_handler : fileQueued,
			file_queue_error_handler : function(file, errorCode, message){
				(options.queueError || fileQueueError).apply(this, arguments);
			},
			file_dialog_complete_handler : function(numFilesSelected, numFilesQueued){
				(options.dialogComplete || fileDialogComplete).call(this, numFilesSelected, numFilesQueued);
			},
			upload_start_handler : function(file){
				(options.start || uploadStart).apply(this, arguments);
			},
			upload_progress_handler : function(file, bytesComplete, bytesTotal){
				(options.progress || uploadProgress).apply(this, arguments);
			},
			upload_error_handler : function(file, errorCode, message){
				(options.error || uploadError).apply(this, arguments);
			},
			upload_success_handler :  function(file, serverData){
				(options.success || uploadSuccess).apply(this, arguments);
			},
			upload_complete_handler : function(file){
				(options.uploadComplete || uploadComplete).apply(this, arguments)
			},
			queue_complete_handler : function(){
				(options.queueComplete || queueComplete).apply(this, arguments)
			}
		};

		return new SWFUpload(settings);
	}
}
		
function flashChecker() {
	var hasFlash = 0; // 是否安装了flash
	var flashVersion = 0; // flash版本
	var isIE = /* @cc_on!@ */0; // 是否IE浏览器

	if (isIE) {
		var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
		if (swf) {
			hasFlash = 1;
			VSwf = swf.GetVariable("$version");
			flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
		}
	} else {
		if (navigator.plugins && navigator.plugins.length > 0) {
			var swf = navigator.plugins["Shockwave Flash"];
			if (swf) {
				hasFlash = 1;
				var words = swf.description.split(" ");
				for ( var i = 0; i < words.length; ++i) {
					if (isNaN(parseInt(words[i])))
						continue;
					flashVersion = parseInt(words[i]);
				}
			}
		}
	}
	return {
		f : hasFlash,
		v : flashVersion
	};
}