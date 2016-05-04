/**
 * webupload可以带上来展示的
 * zpr
 */
(function( $ ){
	 var editUploader={
	 	 srcs:'',
	 	 emptyImage:'',
	 	 editerShowUrl:'',
	 	 editerOpenUploadWin:function(){
	 	      alert("打开上传窗口");
	 	 },
	 	 uploadSuccess:function(file,response ){
	 	      alert("上传成功");
	 	 },
	 	 uploadError:function(file,code){
	 	      alert("上传失败:"+code); 
	 	 },
	 	 //初始化编辑容器
         initEditer:function(){
         	 if(!$('#fileContainer').length){
         	    alert("无法初始化editer,缺少fileContainer");
    	    	return null;
         	 }
         	 var $fileContainer = $('#fileContainer'),
         	     $fileList =  $("<ul class='fileList'></ul>").appendTo($fileContainer);
             var temp={};
             /**************方法**************/
             //新增文件
             temp.addFile = function (src){
             	       var createUUid = function(){
					        var id = setTimeout('0');  
					        clearTimeout(id);  
					        return id;  
				        },
				        path = editUploader.editerShowUrl.replace("$param",src),
				        uuid=createUUid(),
				        $li = $( '<li id="'+uuid+'">' +
				            '<p class="imgWrap"><img src="' +path+ '" width="110px" height="110px"></p>'+
				            '<input type="hidden" name="img" value="'+src+'">'+
				            '</li>' ),
				        $btns = $('<div class="file-panel">' +
				            '<span class="cancel">删除</span>' +
				            '</div>').appendTo( $li );
				    $li.on( 'mouseenter', function() {
				        $btns.stop().animate({height: 30});
				    });
				
				    $li.on( 'mouseleave', function() {
				        $btns.stop().animate({height: 0});
				    });
				    $btns.on( 'click', 'span', function() {
				        var index = $(this).index();
				        switch ( index ) {
				            case 0:
				                temp.removeFile(uuid);
				                return;
				
				            case 1:
				            	alert("1");
				                break;
				
				            case 2:
				                alert("2");
				                break;
				        }
				    });
				    $('#empty').before($li);
			  }
			 //删除文件
			 temp.removeFile =function( id ) {
				   var $li = $('#'+id);
                   $li.off().find('.file-panel').off().end().remove();
			 }
             /**************结束**************/
			 init = function(){
			 	 //增加空白页
				 var $empty = $( '<li id="empty">' +
				            '<p class="imgWrap" style="margin:12px 0;"><img src="'+editUploader.emptyImage+'"></p>'+
				            '</li>' ).appendTo($fileList);;
				 $empty.on('click',function(){
				     editUploader.editerOpenUploadWin();
				 });
				 //初始化,文件
				 if(editUploader.srcs!=''){
				 	var srcArray = editUploader.srcs.split(",");
				 	for(var i=0;i<srcArray.length;i++){
				 	    var src=srcArray[i];
				 	    if(src!=null&&src!='')
				 	       temp.addFile(src);
				 	}
				 }
			 }
			 init();
             return temp;
         },
         //初始化简单上传组件
         initUploader:function(options){
         	 if(!$('#uploader').length){
         	    alert("无法初始化uploader,缺少uploader");
    	    	return null;
         	 }
         	//初始化参数
         	var $uploader = $('#uploader'),
	        $list = $('<div id="thelist" class="uploader-list"></div>').appendTo($uploader),
	        $btns = $('<div class="btns">' +
                      '<div id="picker" style="float:left;width:100px;">选择文件</div>'+
                      '<button id="ctlBtn" class="btn btn-default">开始上传</button>'+
                      ' </div>').appendTo($uploader),
	        $btn = $('#ctlBtn'),
	        state = 'pending',
         	defaultOptions = {
		        resize: false,
		        swf: '../Uploader.swf',
		        server: '',
		        fileNumLimit: 5,
                fileSizeLimit: 200*1024*1024,   // 200 M,200 * 1024 * 1024, 
                fileSingleSizeLimit: 50 * 1024 * 1024,    // 50 M 50 * 1024 * 1024
		        pick: '#picker'
         	 },
         	 uploader;
             opts = $.extend(defaultOptions, options);
             //创建上传组件
             uploader = WebUploader.create(opts);
             //加入队列前判断
             uploader.on('beforeFileQueued',function(file){
             var length = $("#fileContainer").find("input[name='img']").length;
		        if(length==opts.fileNumLimit){
		         	alert("最多只能上传:"+opts.fileNumLimit+"个文件");
		         	return false;
		        }else{
		        	return true;
		        }
             });
             // 当有文件添加进来的时候
		     uploader.on( 'fileQueued', function( file ) {
		     	var $div=$( '<div id="' + file.id + '" class="item">' +
		            '<h4 class="info">' + file.name + '</h4>' +
		            '<p class="state">等待上传...</p>' +
		            '<p class="removeI">移除...</p>' +
		        '</div>' );
		        $div.find('.removeI').on('click',function(){
		           uploader.removeFile(file,true);
		        });
		        $list.append($div);
		     });
	         //当文件被移除队列后触发。
	         uploader.onFileDequeued = function( file ) {
	            state='pedding';
	            var $div = $('#'+file.id);
                $div.remove();
	        };
		     // 文件上传过程中创建进度条实时显示。
		     uploader.on( 'uploadProgress', function( file, percentage ) {
		         var $li = $( '#'+file.id ),
		             $percent = $li.find('.progress .progress-bar');
	
		         // 避免重复创建
		         if ( !$percent.length ) {
		             $percent = $('<div class="progress progress-striped active">' +
		               '<div class="progress-bar" role="progressbar" style="width: 0%">' +
		               '</div>' +
		             '</div>').appendTo( $li ).find('.progress-bar');
		         }
	
		         $li.find('p.state').text('上传中');
	
		         $percent.css( 'width', percentage * 100 + '%' );
		     });
		     uploader.on( 'uploadSuccess', function( file,response  ) {
		         $( '#'+file.id ).find('p.state').text('已上传');
		         editUploader.uploadSuccess(file,response);
		     });
	
		     uploader.on( 'uploadError', function( file,code ) {
		         $( '#'+file.id ).find('p.state').text('上传出错');
		         editUploader.uploadError(file,code);
		     });
	
		     uploader.on( 'uploadComplete', function( file ) {
		         $( '#'+file.id ).find('.progress').fadeOut();
		     });
		     uploader.on( 'all', function( type ) {
		         if ( type === 'startUpload' ) {
		             state = 'uploading';
		         } else if ( type === 'stopUpload' ) {
		             state = 'paused';
		         } else if ( type === 'uploadFinished' ) {
		             state = 'done';
		         }
	
		         if ( state === 'uploading' ) {
		             $btn.text('暂停上传');
		         } else {
		             $btn.text('开始上传');
		         }
		     });
		     uploader.onError = function( code ) {
		            switch(code){
		                case 'Q_EXCEED_NUM_LIMIT':
		                     alert("超出上传个数");
		                     break;
		                case 'Q_EXCEED_SIZE_LIMIT':
		                     alert("超出上传大小");
		                     break;
		                case 'Q_TYPE_DENIED':
		                     alert("文件类型不满足");
		                     break;
		                case 'F_DUPLICATE':
		                     alert("此文件已存在");
		                     break;
		                default:
		                     alert("Error:"+code);
		                     break;
		            }
		     };
		     $btn.on( 'click', function() {
		         if ( state === 'uploading' ) {
		             uploader.stop();
		         } else {
		         	 uploader.upload();
		         }
		     });
		     return uploader;
         }
     };
     window.editUploader = editUploader;
})( jQuery );