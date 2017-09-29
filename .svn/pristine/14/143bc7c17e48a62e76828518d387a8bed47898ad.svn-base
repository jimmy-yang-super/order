$(document).ready(function(){
	var QUERY = {
			// 初始化
			init: function(){
				QUERY.selectDiv();
			},
			selectDiv:function(){
				$(".nice-select").each(function(index,element){
					var val = $(this).prev().val();
					var changeFlag = true;
					$(this).find("ul li").each(function(index, ele){
						if($(ele).attr("data-value") == val){
							changeFlag = false;
							$(this).parents('[name="nice-select"]').find('input').val($(this).text());
						}
					});
					if(changeFlag == true){
						var first = $(this).find("ul li:first");
						$(this).prev().val(first.attr("data-value"));
						$(this).find("input").val(first.text());
					}
				});
				$('[name="nice-select"]').click(function(e) {
					$('[name="nice-select"]').find('ul').hide();
					$(this).find('ul').show();
					e.stopPropagation();
				});
				$('[name="nice-select"] li').hover(function(e) {
					$(this).toggleClass('on');
					e.stopPropagation();
				});
				$('[name="nice-select"] li').click(function(e) {
					var val = $(this).text();
					var dataVal = $(this).attr("data-value");
					$(this).parents('[name="nice-select"]').find('input').val(val);
					$(this).parents('[name="nice-select"]').prev().val(dataVal);
					$('[name="nice-select"] ul').hide();
					e.stopPropagation();
				});
				$(document).click(function() {
					$('[name="nice-select"] ul').hide();
				});
			}
		};
	QUERY.init();
	window.QUERY = QUERY;
}());

