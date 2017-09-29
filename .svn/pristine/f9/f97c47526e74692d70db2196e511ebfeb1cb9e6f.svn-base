// 经营范围查询弹出框
jQuery(document).ready(function($){

	//open popup
	$('.pop_box1').on('click', function(event){
		event.preventDefault();
		$('.cd-box1').addClass('is-visible');
	});
	//close popup
	$('.cd-box1').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-box1') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	//close popup when clicking the esc keyboard button
	$(document).keyup(function(event){
    	if(event.which=='27'){
    		$('.cd-box1').removeClass('is-visible');
	    }
    });
	$('.cd-box1 .save').on('click', function(event){
		event.preventDefault();
		$('.cd-box1').removeClass('is-visible');
	});
});


// 保存提示弹出层
jQuery(document).ready(function($){
	//open popup
/*	$('.pop_box2').on('click', function(event){
		event.preventDefault();
		$('.cd-box2').addClass('is-visible');
	});*/
	//close popup
	$('.cd-box2').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-box2') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	//close popup when clicking the esc keyboard button
	$(document).keyup(function(event){
    	if(event.which=='27'){
    		$('.cd-box2').removeClass('is-visible');
	    }
    });
	$('.cd-box2 .btn .btn2').on('click', function(event){
		event.preventDefault();
		$('.cd-box2').removeClass('is-visible');
	});
});


// 选择人员弹出层
jQuery(document).ready(function($){
	//open popup
	$('.pop_box3').on('click', function(event){
		event.preventDefault();
		$('.cd-box3').addClass('is-visible');
	});
/*	//close popup
	$('.cd-box3').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-box3') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	//close popup when clicking the esc keyboard button
	$(document).keyup(function(event){
    	if(event.which=='27'){
    		$('.cd-box3').removeClass('is-visible');
	    }
    });*/
});


/**
 * 初始化经营范围事件
 */
function initOperatingRangeEvent(){
   $('.type input').on('click',function(e){
       var $this = $(this);
       var $span = $this.parent();
       var $parent = $span.parent();
       var $showArea = $('#' + $parent.attr('bindShowArea'));
       var namespace = $parent.attr('namespace');
       var text = $span.text();
       showOrRemoveSelector($this,$showArea,namespace,text);
       e.stopPropagation();
   });

   // 选中联动
   $('.type p').click(function() {
       var $this = $(this);
       var $checkbox = $this.children('input[type="checkbox"]');
       var $parent = $this.parent();
       var $showArea = $('#' + $parent.attr('bindShowArea'));
       var namespace = $parent.attr('namespace');
       var text = $this.text();
       $checkbox.prop('checked',!$checkbox.prop('checked'));
       showOrRemoveSelector($checkbox,$showArea,namespace,text);
   });

   $('.b_choiceArea ul').on('click','li',function(){
       var $this = $(this).remove();
       var value = $this.attr('bindValue');
       var namespace = $this.attr('namespace');
       $('.type[namespace="'+namespace+'"] p input[value="'+value+'"]').attr('checked',false);
       initSelectedOperatingRange();
   });
   // 蓝色显示区域
}
/**
* 初始化，选中的经营范围
*/
function initSelectedOperatingRange(){
	// 判断是否已经选中
	$(".jy_box").find(".right.type p").each(function(index, ele){
		var operatingRangeId = $(ele).find("input").val();
		var selecedObj = $("#scopeBusiness").find("li[bindvalue='" + operatingRangeId + "']");
		if(selecedObj != undefined && selecedObj.length > 0){
			$(ele).find("input").attr("checked", true);
		}
	});
}

/**
* 动态加载展示经营范围
* @param operatingRange
*/
function operatingRangeShow(operatingRange){
	if(operatingRange == undefined || operatingRange == ""){
		return;
	}
	var operatingRangeList = operatingRange.operatingRangeList;
	var html = "";
	if(operatingRangeList != undefined && operatingRangeList.length > 0){
		for(var i = 0; i < operatingRangeList.length; i++ ){
			html += "<p><input type=\"checkbox\" name=\"chkItem\" value=\"" + operatingRangeList[i]["name"] + "\" /><label>" + operatingRangeList[i]["name"] + "</label></p>";
		}
	}
	$(".jy_box").find(".right.type").html(html);
	initOperatingRangeEvent();
	initSelectedOperatingRange();
}

//蓝色显示区域
function showOrRemoveSelector($checkbox,$showArea,namespace,text) {
   var value = $checkbox.val();
   if ($checkbox.get(0).checked) {
       var str = '<li class="bgBlue" bindValue="' + value +'" namespace="' + namespace + '">' + text + '</li>';
       $showArea.append(str);
   } else {
       $showArea.children('[namespace="'+namespace+'"][bindValue="'+value+'"]').remove();
   }
}

