
$("#shubing-toux-doc").live("change",function(){
	
	//var filepath = $("input[name='myFile']").val();    //可以直接使用this获取
    var filepath = $(this).val();
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        alert("图片限于png,gif,jpg,bmp,jpeg格式");
        $("#fileSize").val(" ");
        return false;
    }
    var file_size = 0;
    if ($.browser.msie) {//IE
        var img = new Image();
        img.src = filepath;
        while (true) {
            if (img.fileSize > 0) {
                if (img.fileSize > 50) {
                    alert("图片不大于50KB！小本网站，截张图就好啦！");
                } else {
                    var num03 = img.fileSize / 1024;
                    num04 = num03.toFixed(2)
                    $("#fileSize").text(num04 + "KB");
                }
                break;
            }
        }
    } else {
        file_size = this.files[0].size;
        var size = file_size / 1024;
        if (size > 50) {
            alert("上传的图片大小不能超过50KB！小本网站，截张图就好啦！");
        } else {
            var num01 = file_size / 1024;
            num02 = num01.toFixed(2);
            $("#fileSize").text(num02 + " KB");
        }
    }
	//以上是限制图片格式和大小
	
    //以下是将图片显示到网页
	var docObj=document.getElementById("shubing-toux-doc");
	var imgObjPreview=document.getElementById("shubing-toux-preview");
	if(docObj.files &&docObj.files[0])
	{
//		火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '150px';
		imgObjPreview.style.height = '180px';
//		imgObjPreview.src = docObj.files[0].getAsDataURL();

//		火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	}
	else
	{
//		IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
//		必须设置初始大小
		localImagId.style.width = "150px";
		localImagId.style.height = "180px";
//		图片异常的捕捉，防止用户修改后缀来伪造图片
		try{
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}
		catch(e)
		{
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
});
