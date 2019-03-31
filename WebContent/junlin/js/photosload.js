 function previewImage(file,imgheadId)
        {
	 
	 	var files = file.files[0];//上传的图片的所有信息
	 	console.log(file.files[0]);
	 	
	         //在此限制图片的大小
	         var imgSize = files.size;
	         console.log(imgSize);
	         //35160  计算机存储数据最为常用的单位是字节(B)
	         //在此处我们限制图片大小为2M
	         if(imgSize>2*1024*1024){
	        	 laywarn('上传的图片的大于2M,请重新选择');
	         $(file).val('');
	         return false;
	         }
	         
	         
          var MAXWIDTH  = 90; 
          var MAXHEIGHT = 90;
          var div = document.getElementById('preview'+imgheadId);
          if (file.files && file.files[0])
          {//onclick=$("#previewImg'+imgheadId+'").click()
              div.innerHTML ='<img id='+'imghead'+imgheadId+'  alt='+imgheadId+'>';
              var img = document.getElementById('imghead'+imgheadId);
              img.onload = function(){
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top+'px';
              }
              var reader = new FileReader();
              reader.onload = function(evt){img.src = evt.target.result;}
              reader.readAsDataURL(file.files[0]);
          }
          else //兼容IE  
          {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id='+'imghead'+imgheadId+' alt='+imgheadId+'>';
            var img = document.getElementById('imghead'+imgheadId);
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
          }
          
          if(($("#previewImg"+imgheadId).val()=="")==false){
          	//alert(0);
          	var $del='<div onclick="delPic(this)" class="delpic-div"><i class="fa fa-times"></i></div>';
          	var nextid=imgheadId+1;
          	var $div='<div class="big-photo"><div class="preview" id="preview'+nextid+'"><img id="imghead'+nextid+'" border="0" src="../../../img/photo_icon.jpg" width="90" height="90" onclick="$(\'#previewImg'+nextid+'\').click();" alt="'+nextid+'"></div><input type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="previewImage(this,'+nextid+')" style="display: none;" id="previewImg'+nextid+'" name="previewImg"></div>';
          	$(file).parent().append($del);
//          	console.log($('.showDiv img'));
          	console.log("file:"+isnotEmpty($(".showDiv input:first").val()));
          	if($('.showDiv').children('.big-photo').length<10&&isnotEmpty($(".showDiv input:last").val())){

          		$(".showDiv").append($div);
          	}
          }
        }
        function clacImgZoomParam( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            if( width>maxWidth || height>maxHeight ){
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;
                
                if( rateWidth > rateHeight ){
                    param.width =  maxWidth;
                    param.height = Math.round(height / rateWidth);
                }else{
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param;
        }
