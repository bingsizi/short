/**
 * zpr
 */
;var Popbox = (function($){
	/**
	 * 获得动态参数
	 */
	function getArgumentsValue(values){
		var v = {};
		if(values.length==1){
			v.title = "系统消息";
			v.msg = values[0];
		}else if(values.length==2){
			v.title = values[0];
			v.msg = values[1];
		}
		return v;
	}
	/**
	 * 上右弹出
	 */
	function topRight(){
		var v = getArgumentsValue(arguments),
		    title = v.title,
		    msg = v.msg;
		$.messager.show({
			title:title,
			msg:msg,
			showType:'fade',
			style:{
				left:'',
				right:0,
				top:document.body.scrollTop+document.documentElement.scrollTop,
				bottom:''
			}
		});
	}
	/**
	 * 上中弹出
	 */
	function topCenter(){
		var v = getArgumentsValue(arguments),
	    title = v.title,
	    msg = v.msg;
		$.messager.show({
			title:title,
			msg:msg,
			showType:'fade',
			style:{
				right:'',
				top:document.body.scrollTop+document.documentElement.scrollTop,
				bottom:''
			}
		});
	};
	/**
	 * 下右弹出
	 */
	function bottomRight(){
		var v = getArgumentsValue(arguments),
	    title = v.title,
	    msg = v.msg;
		$.messager.show({
			title:title,
			msg:msg,
			showType:'fade'
		});
	}
	return {
		topRight:topRight,
		topCenter:topCenter,
		bottomRight:bottomRight
	}
})(jQuery);

