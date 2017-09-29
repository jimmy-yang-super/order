$(document).ready(function(){
	//open popup
	$('.pop_box').on('click', function(event){
		event.preventDefault();
		var fileIds = $(this).attr("data-fileIds");
		if(fileIds == undefined || fileIds == ""){
			alert("没有附件");
			return;
		}
		var fileIdArray = fileIds.split(";");
		
		var fileHtml = "";
		for(var i=0;i<fileIdArray.length;i++){
			fileHtml += "<li ><img src=\"/file/download/" + fileIdArray[i] + "?maxLength=300 \"></li>";
		}
		$('.cd-popup').find("ul[class='imglist']").html(fileHtml);
		$('.cd-popup').addClass('is-visible');
		
	    //默认状态下左右滚动
        $("#s1").unbind("xslider").xslider({
            unitdisplayed:1,
            movelength:1,
            unitlen:690,
            autoscroll:null
        });
	});
	
	//close popup
	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	//close popup when clicking the esc keyboard button
	$(document).keyup(function(event){
    	if(event.which=='27'){
    		$('.cd-popup').removeClass('is-visible');
	    }
    });
	
	
})