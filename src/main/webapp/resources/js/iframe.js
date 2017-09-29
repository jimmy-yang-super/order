//gusc 2015-02-01
$(function() {
	//dom
	var bgAb = $("#bg-ab"),
		selType = $(".selType"),
		selDtil = $(".selDtil"),
		numSc = $("#selDtil"),
		addBtn = $("#addBtn"),
		addWin = $("#addWin"),
		fqWin = $("#fqWin"),
		fqWinArea = fqWin.find("textarea"),
		pageNo = $("#pageNo"),
		timeInput = $(".timeInput"),
		sTime = $("#sTime"),
		eTime = $("#eTime"),
		sTimeDiv = $("#sTimeDiv"),
		eTimeDiv = $("#eTimeDiv"),
		timeDiv = $(".timeDiv"),
		yewuSel = $("#yewuSel"),
		yewuDtil = $("#yewuDtil"),
		selSear = $(".selSear"),
		tabSelCon = $(".tabSelCon");
	
	var fucn = {
		//时间结构渲染
		timeRender: function(year,month,date) {
			var nDate = new Date(year,month-1,1),//月份-1
				day = nDate.getDay();//该月1号周几
			var str = '<p class="tTle"><span class="year">'+year+'</span>年<span class="month">'+month+'</span>月<i class="prev"></i><i class="next"></i></p><ul class="indexTle"><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul><ul class="timeCon">';
			var rnTag = false;//闰年tag
			if(year%4 === 0 && (year%100 !== 0 || year%100 === 0)) {
				rnTag = true;
			};
			var pMonthBg = 0,//上个月的填充从几号开始
				nMonthBg = 0,//下个月要填充几天
				cMonthDate = 31;//该月共有几天
			if(day) {//非周日 需要填充上月
				switch(month) {
				case 1:
				case 2:
				case 4:
				case 6:
				case 8:
				case 9:
				case 11:
					pMonthBg = 32 - day;
					break;
				case 5:
				case 7:
				case 10:
				case 12: 
					pMonthBg = 31 - day;
					break;
				case 3:
					if(rnTag){
						pMonthBg = 30 - day;
					}else{
						pMonthBg = 29 - day;
					};
				}
				for(var i=0; i<day; i++) {
					str += '<li class="oMonth">'+(pMonthBg+i)+'</li>';
				};
			};
			switch(month) {
			case 4:
			case 6:
			case 9:
			case 11: 
				cMonthDate = 30;
				break;
			case 2:
				if(rnTag){
					cMonthDate = 29;
				}else{
					cMonthDate = 28;
				};
			};
			for(var i=0;i<cMonthDate;i++){
				if(i+1 === date){
					str += '<li class="sel">'+(i+1)+'</li>';
				}else{
					str += '<li>'+(i+1)+'</li>';
				};
			};
			if(day) {//非周日
				nMonthBg = (7 - ((cMonthDate % 7 + day) % 7)) % 7;
			}else{//周日
				nMonthBg = (7 - (cMonthDate % 7)) % 7;
			};
			for(var i=0; i<nMonthBg; i++){
				str += '<li class="oMonth">'+(i+1)+'</li>';
			};
			str += '</ul>';
			return str;
		},
		addWinRender: function() {
			addWin.find("input").val("");
			addWin.find("textarea").val("");
			addWin.find(".b-red").removeClass("b-red");
			addWin.find("li.sel").removeClass("sel");
			yewuSel.children("span").html("");
		},
		//动态控制iframe的高度
		ifrHeight: function() {
			var wtd = window.top.document;
			var ifrNode = wtd.find("#ifrCon");
			var height = +$("body").height + 20 + "px";
			ifrNode.css("height",height);
		},
		timeChange: function(node,year,month,date) {
			
		},
		updateIfrHeigh: function () {
			var wtd = window.top.document,
				ifrNode = $(wtd).find("#ifrCon");
			setTimeout(function() {
				ifrNode.css("height",+$("body").height() + 30 + "px");
			},500);
		}
	};
	(function() {
		var time = new Date(),	
			year = +time.getFullYear(),
			month = +time.getMonth() + 1,
			date = +time.getDate();
			// day = +time.getDay();
			
		var htmlStr = fucn.timeRender(year,month,date);
		$.each(timeDiv,function() {
			$(this).html(htmlStr);
		});
	})();
	fucn.updateIfrHeigh();//iframe高度自适应
	
	$(document).click(function() {
		selDtil.hide();
		timeDiv.hide();
		var iUp = selType.children("i.up");
		$.each(iUp,function() {
			this.className = "down";
		});
	});
	//delegate 1.seltype 2.跟进次数 3.满意度
	$(document).delegate("div.selType","click",function(e) {
		e.stopPropagation();
		// $(".selDtil:visible").hide();
		var selDtil = $(this).children(".selDtil");
		if(selDtil.is(":visible")) {
			selDtil.hide();
			$(this).children("i")[0].className = "down";
		}else {
			$(".selDtil:visible").hide();
			selDtil.show();
			if($(this).children("i").length) {//有些不能点击
				$(this).children("i")[0].className = "up";
			};
		};
	}).delegate(".gzNum>a","click",function() {
		var jlDeatil = $(this).parents("dl").find("dd.jlDeatil");
		var orderid = $(this).parents("dl").find("dt").text();
		var url = "/kefu/follow/"+orderid+"/"+$("#empid").val();
		
		if($(this).hasClass("sel")) {
			$(this).removeClass("sel");
			jlDeatil.hide();
			jlDeatil.html("");
		}else {
			$(this).addClass("sel");
			$.post(url,	function(data){
				jlDeatil.show();
				jlDeatil.html(data);
			});
		};
		fucn.updateIfrHeigh();
	
		/**
		var jlDeatil = $(this).parents("dl").find("dd.jlDeatil");
		if($(this).hasClass("sel")) {
			$(this).removeClass("sel");
			jlDeatil.hide();
		}else {
			$(this).addClass("sel");
			jlDeatil.show();
		};
		*/
	}).delegate(".yxSel>a","click",function() {
		var o = $(this);
		if(!o.hasClass("sel")) {
			o.siblings("a.sel").removeClass("sel");
			o.addClass("sel");//.className = "sel";
			o.parent().parent().find("input[name='intentcode']").val(o.attr("v"));
//			$("intentcode").val(o.attr("v"));
		};
		/**
		if(!$(this).hasClass("sel")) {
			$(this).siblings("a.sel").removeClass("sel");
			this.className = "sel";
		};
		*/
	}).delegate("a.dropBtn","click",function() {
		//客户id？
		fqWinArea.val("");
		fqWin.show();
		bgAb.show();
	}).delegate("dd.aOperDD a","click",function() {
		var cName = "ddDetail",
			dlNode = $(this).parents("dl");
		if($(this).hasClass("jzBtn")) {
			cName = "jzDetail";
		};
		var detailNode = dlNode.children("dd[nor]"),
			dNode = dlNode.children("dd." + cName);
		detailNode.hide();
		if($(this).hasClass("sel")) {
			$(this).removeClass("sel");
		}else{
			$(this).siblings("a.sel").removeClass("sel");
			$(this).addClass("sel");
			dNode.show();
		};
	}).delegate("dd.operDD a","click",function() {
		// var cName = null;
		if($(this).hasClass("pdBtn")){
			var sNode = $(this).siblings(".pdDetail");
			if($(this).hasClass("sel")){
				sNode.hide();
				$(this).removeClass("sel");
			}else{
				sNode.show();
				$(this).addClass("sel");
			};
		};
	});
	//下拉条件选择
	selDtil.delegate("li","click",function(e) {
		var txt = $(this).text();
		var val = $(this).attr("val");
			selNode = $(this).parents(".selType");
		if(this.className === "noHave") {
			txt = $(this).attr("dtxt");
		};
		selNode.children("span").html(txt);
		selNode.children("span").attr("val",val);
		if($(this).parents("#phDtil").length) {
			//search tabSelCon
			//ddlyCon
			//fflxCon
			var cdom = $(this).attr("cdom");
			tabSelCon.hide();
			selSear.find("#" + cdom).show();
		};
	});
	addBtn.click(function() {
		fucn.addWinRender();
		$(".moneyLi").hide();
		$("#neworderid").val("0");
		$("#newcusid").val("0");
		bgAb.show();
		addWin.show();
	});
	bgAb.click(function() {
		fucn.addWinRender();
		bgAb.hide();
		addWin.hide();
		fqWin.hide();
	});
	addWin.find("a.canBtn").click(function() {
		fucn.addWinRender();
		bgAb.hide();
		addWin.hide();
		
	});
	//asc desc 跟进次数排序
	numSc.click(function() {
		//ajax?
		
	});
	//fqWin close 
	fqWin.find("i.colseIcon").click(function (){
		bgAb.hide();
		fqWin.hide();
	});
	//yewuSel li
	yewuDtil.delegate("li","click",function() {
		var pNode = $(this).parents("div.selCon");
		if($(this).hasClass("sel")) {
			$(this).removeClass("sel");
		}else {
			if(pNode.siblings("div.selCon").find("li.sel").length === 0){//只能多选选择同一个大类别下的
				$(this).addClass("sel");
			};
		};
		var txt = yewuDtil.find("li.sel:first").text();
		if(txt) {
			txt = txt.substring(0,3) + "...";
		}else{
			txt = "";
		};
		yewuSel.children("span").text(txt);
		return false;
	});
	yewuDtil.click(function(e) {
		e.stopPropagation();
	});
	//pageNo 页码
	pageNo.delegate("a","click",function() {
		if($(this).hasClass("nb")) {
			return;
		};
		if($(this).attr("goto")){
		
		}else{
			/**
			if(!$(this).hasClass("cur")) {
				$(this).siblings("a.cur").removeClass("cur");
				$(this).addClass("cur");
			};
			*/
		};
	});
	
	//timeInput
	timeInput.delegate("input","click",function(e) {
		e.stopPropagation();
		var tId = this.id;
		if(tId === "sTime") {
			sTimeDiv.show();
			eTimeDiv.hide();
		}else{
			sTimeDiv.hide();
			eTimeDiv.show();
		};
	});
	timeDiv.click(function(e) {
		e.stopPropagation();
	});
	
	timeDiv.delegate(".timeCon li","click",function(e) {
		e.stopPropagation();
		var timeNode = $(this).parents(".timeDiv"),
			tId = timeNode.attr("id");
		var year = +timeNode.find("span.year").text(),
			month = +timeNode.find("span.month").text(),
			date = +$(this).text();
		if($(this).hasClass("oMonth")) {
			if(date > 15) {
				if(month === 1) {
					month = 12;
					year--;
				}else{
					month--;
				};
			}else {
				if(month === 12) {
					month = 1;
					year++;
				}else{
					month++;
				};
			};
			timeNode.html(fucn.timeRender(year,month,date));
		}else{
			$(this).siblings("li.sel").removeClass("sel");
			$(this).addClass("sel");
		};
		if(tId === "sTimeDiv") {
			sTime.attr("value",year + "-" + month + "-" + date);
			sTimeDiv.hide();
			eTimeDiv.show();
		}else{
			eTime.attr("value",year + "-" + month + "-" + date);
			eTimeDiv.hide();
		};
	}).delegate(".tTle i","click",function(e) {
		e.stopPropagation();
		var cName = this.className;
		var timeNode = $(this).parents(".timeDiv");
			// tId = timeNode.attr("id");
		var year = +timeNode.find("span.year").text(),
			month = +timeNode.find("span.month").text(),
			date = +timeNode.find("li.sel").text();
		if(cName === "prev") {
			if(month === 1) {
				month = 12;
				year--;
			}else{
				month--;
			};
		}else {
			if(month === 12) {
				month = 1;
				year++;
			}else{
				month++;
			};
		};
		timeNode.html(fucn.timeRender(year,month,date));
	});
	//add form校验
	$(document).delegate(".infoInput input","blur",function()
	{
		if($(this).parents("li.moneyLi").length === 0) {
			var tId = this.id;
			var reg = '';
			switch(tId) {
			case "phonenum":
				reg = /^\d+(-)?\d+$/g;
				break;
			case "email":
				reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				break;
			default: 
				break;
			};
			if(reg) {
				var value = $(this).val();
				if(!reg.test(value)) {
					$(this).parent("div.infoInput").addClass("b-red");
				};
			};
		};
	}).delegate(".infoInput input","focus",function()
	{
		var pNode = $(this).parent("div.infoInput");
		if(pNode.hasClass("b-red")) {
			pNode.removeClass("b-red");
		};
	});
	/**
	$("#ordergubutton").click(function(){
		var orderid = $("#giveuporderid").val();
		var url = "/kefu/giveup/"+orderid;
		$.ajax({url:url,
			data:{reson:$("#giveupreson").val()},
			dataType:"json",
			success:function(data){
				if(data.ret != "ok"){
					alert(data.msg);
				}else{
					window.href.reload();
				}
			}});
	});
	*/
});
