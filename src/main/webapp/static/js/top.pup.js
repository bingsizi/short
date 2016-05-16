;(function(){
	var win = window,
    flag = false;
//判断是否是打开的窗口
while(null != win.opener && win.opener != win){
	win = win.opener;
	flag = true;
}
//判断是否是在框架中
while(win.parent != win){
	win = win.parent;
	flag = true;
}
//判断是否是顶层窗口
while(win.top != win){
	win = win.top;
	flag = true;
}
if(flag){
	url = window.location.href;
	win.location.href=url;
}
})();
