$(document).ready(function(){
    var $bar = $('.nav');
    var $contenForm = $('.contentForm');
    var $save = $('.save');
    var $choiceSpans = $('.b_i_operatingRangeItemCont span');

    $bar.next().css('display','none');
    $bar.on('click',function(){
        var $this = $(this);
        var $showArea = $this.next();
        if ($showArea.css('display') == 'none') {
            $showArea.slideDown('1000');
            $this.removeClass('navHover');
        } else {
            $showArea.slideUp('1000');
            $this.addClass('navHover');
        }
    });

    $contenForm.submit( function(){
        var params = $(this).serialize();
        alert(params);

        return false;
    })

    $save.on('click',function(e){
        e.stopPropagation();
    })

    $('.type_1 span input').on('click',function(e){
        var $this = $(this);
        var $span = $this.parent();
        var $parent = $span.parent();
        var $showArea = $('#' + $parent.attr('bindShowArea'));
        var namespace = $parent.attr('namespace');
        var text = $span.text();

       // showOrRemoveSelector1($this,$showArea,namespace,text);
        e.stopPropagation();
        var val = $this.attr("value");
        var tm =  $(this).attr("checked");
        
        if(tm =="checked"){
        	dyncjyfw(val);
        }else{
        	$("#qitywcheck").html("");
			$("#jyfw").hide();
			$("#jyyl").hide();
        }
       // dyncjyfw(val);
        
    });

    $('.b_i_or_choiceArea ul').on('click','li',function(){
        var $this = $(this).remove();
        var value = $this.attr('bindValue');
        var namespace = $this.attr('namespace');
        $('.type[namespace="'+namespace+'"] span input[value="'+value+'"]').attr('checked',false);
    });




    function showOrRemoveSelector1($checkbox,$showArea,namespace,text) {

        var value = $checkbox.val();

        if ($checkbox.get(0).checked) {
            var str = '<li class="bgBlue" bindValue="' + value +'" namespace="' + namespace + '">' + text + '</li>';
            // $showArea.append(str);
            
            //var str = '<li class="bgRed"  style="float"  bindValue="' + value +'" namespace="' + namespace + '">' + text + '</li>';
            $showArea.html(str);
        } else {
            $showArea.children('[namespace="'+namespace+'"][bindValue="'+value+'"]').remove();
        }
       // becomeButon('jybutton');
    }

    $('.zy_box span input').on('click',function(e){
        var $this = $(this);
        var $span = $this.parent();
        var $parent = $span.parent();
        var $showArea = $('#' + $parent.attr('bindShowArea'));
        var namespace = $parent.attr('namespace');
        var text = $span.text();

        showOrRemoveSelector1($this,$showArea,namespace,text);
        e.stopPropagation();
    });

    $('.zy_box span').click(function() {
        var $this = $(this);
        var $checkbox = $this.children('input[type="checkbox"]');

        var $parent = $this.parent();
        var $showArea = $('#' + $parent.attr('bindShowArea'));
        var namespace = $parent.attr('namespace');
        var text = $this.text();

        $checkbox.prop('checked',!$checkbox.prop('checked'));
        showOrRemoveSelector1($checkbox,$showArea,namespace,text);
    });


    $('.b_i_or_choiceArea ul').on('click','li',function(){
        var $this = $(this).remove();
        var value = $this.attr('bindValue');
        var namespace = $this.attr('namespace');
        $('.zy_box[namespace="'+namespace+'"] span input[value="'+value+'"]').attr('checked',false);
    });

    $(".addBtn input").on('click',function(){
        var $this = $(this);
        $this.addClass('hover').siblings().removeClass('hover');
        var tpl = $('#' + $this.attr('bindTpl')).html();

        $('#shareholders').append(tpl);
    })
    $('#shareholders').on('mouseenter','>div',function(){
        $(this).addClass('bgPink').siblings().removeClass('bgPink');
    });

    $('#shareholders').on('click','.delShareholderIcon',function(){
        $(this).parent().remove();
        return false;
    })
    $('.radioMargin span').on('click',function(){
        var $this = $(this);
        index = $this.index();
        // $this.next().attr('checked',true);
        $('.adressWraper div').eq(index).show().siblings().hide();
        if(index =="1"){
      	   $("#dydlinfo").hide();
      	   $("#isfuhq").show();
  		   $("#nofuhq").hide();
  		   map = new Map();
  		   jyfwfhq(map);
  		 $("#disshdz").show()
         }else{
         	 $("#disshdz").hide();
         	 $("#isfuhq").hide();
    		 $("#nofuhq").show();
    		 map = new Map();
    		 jyfwinit(map);
         }
        //becomeButon('gsdzbutton');
    })
    $('.radioMargin1 span').on('click',function(){
        var $this = $(this);
        index = $this.index();
        // $this.next().attr('checked',true);
        $('.adressWraper1 div.ownAdress').eq(index).show().siblings().hide();
       // becomeButon('fdbutton');
    })
    $('.radioMargin2 span').on('click',function(){
        var $this = $(this);
        index = $this.index();
        // $this.next().attr('checked',true);
        $('.adressWraper2 div.ownAdress').eq(index).show().siblings().hide();
       // becomeButon('fdbutton');
    })
})

    function dyncjyfw(hylx){
    	if(map != null){
    		var tmp = map.get(hylx);
    		if(tmp != null && tmp !=""){
    			var mt = tmp.split("&");
    			var zhuyht ="";
    			var qityw ="";
    			for(var i =0;i<mt.length;i++){
    				zhuyht = zhuyht +"<span><input type=\"checkbox\" name=\"zhuyyw\" value=\""+mt[i]+"\" /><label>"+mt[i]+"</label></span>";
    				qityw = qityw +"<span><input type=\"checkbox\" name=\"qityw\" value=\""+mt[i]+"\" /><label>"+mt[i]+"</label></span>"
    		    }
    			//$("#zhuyywcheck").append(zhuyht);
    			$("#qitywcheck").html(qityw);//wulei
    			
    			var pqityw="";
    			$(".bgBlue").each(function() {
					$("input:checkbox[value='"+$(this).attr("bindValue")+"']").attr("checked","true");
				});
    				if($(".bgBlue").length>0){
						   $("#jyyl").show();
							$("#disjyyl").show();
					}
	
    			 $("#jyfw").show();
    			 initYyfw();
    		}
    	}
    }

    function showOrRemoveSelector($checkbox,$showArea,namespace,text) {

        var value = $checkbox.val();
        if ($checkbox.get(0).checked) {
            var str = '<li class="bgBlue" bindValue="' + value +'" namespace="' + namespace + '">' + text + '</li>';
            $showArea.append(str);
            
            // var str = '<li class="bgRed" bindValue="' + value +'" namespace="' + namespace + '">' + text + '</li>';
            // $showArea.append(str);
        } else {
            $showArea.children('[namespace="'+namespace+'"][bindValue="'+value+'"]').remove();
        }
        //becomeButon('jybutton');
    }

function initYyfw(){
    $('.type span input').on('click',function(e){
        var $this = $(this);
        var $span = $this.parent();
        var $parent = $span.parent();
        var $showArea = $('#' + $parent.attr('bindShowArea'));
        var namespace = $parent.attr('namespace');
        var text = $span.text();
        $("#jyyl").show();
    	$("#disjyyl").show();
        showOrRemoveSelector($this,$showArea,namespace,text);
        e.stopPropagation();
    });
}