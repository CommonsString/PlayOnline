<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>XMLHttpRequest上传文件进度实现</title>
    <script type="text/javascript" src="js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript">
        var xhr;
        var ot;  //上传时间
        var oloaded; //上传文件大小
        //上传文件方法
        function UpladFile() {
/*            var fileObj1 = document.getElementById("file").files[0]; // js 获取文件对象
            var fileObj2 = document.getElementById("file2").files[0];*/
            
            var url = "uploadFile"; // 接收上传文件的后台地址 
/*            alert("1" + fileObj2);
            alert("2" + fileObj2);*/
            var form = new FormData(document.getElementById("form")); // FormData 对象
            
            xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
            xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
            
            xhr.onload = uploadComplete; //请求完成
            xhr.onerror =  uploadFailed; //请求失败
            xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
            xhr.upload.onloadstart = function(){//上传开始执行方法
                ot = new Date().getTime();   //设置上传开始时间
                oloaded = 0;//设置上传开始时，以上传的文件大小为0
            };
            xhr.send(form); //开始上传，发送form数据
        }
        //上传进度实现方法
        function progressFunction(evt) {
            
             var progressBar = document.getElementById("progressBar");
             var percentageDiv = document.getElementById("percentage");
             
             if (evt.lengthComputable) {
                 progressBar.max = evt.total;
                 progressBar.value = evt.loaded;
                 percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
             }
            
            var time = document.getElementById("time");
            var nt = new Date().getTime();//获取当前时间
            var pertime = (nt-ot)/1000; 
            ot = new Date().getTime(); //重新赋值时间，用于下次计算
            
            var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b       
            oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
        
            //上传速度计算
            var speed = perload   //pertime;//单位b/s
            var bspeed = speed;
            var units = 'b/s';//单位名称
            if(speed/1024>1){
                speed = speed/1024;
                units = 'k/s';
            }
            if(speed/1024>1){
                speed = speed/1024;
                units = 'M/s';
            }
            speed = speed.toFixed(1);
            //剩余时间
            var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
            time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
               if(bspeed==0)
                time.innerHTML = '上传已取消';
        }
        //上传成功响应
        function uploadComplete(evt) {
         //服务断接收完文件返回的结果
         //    alert(evt.target.responseText);
             alert("上传成功！");
        }
        //上传失败
        function uploadFailed(evt) {
            alert("上传失败！");
        }
          //取消上传
        function cancleUploadFile(){
            xhr.abort();
        }
    </script>
</head>
<body>
    <progress id="progressBar" value="0" max="100" style="width: 300px;"></progress>
    <span id="percentage"></span><span id="time"></span>
    <br /><br />
<!--   视频：  <input type="file" id="file" name="myfile" /> <br />
      图片：  <input type="file" id="file2" name="myfile" /> <br />
   文件标题 <input type="text" id="title" /> <br />
  文件描述  <input type="text" id="discribe" /> <br />
    <input type="button" onclick="UpladFile()" value="上传" />
    <input type="button" onclick="cancleUploadFile()" value="取消" />-->
    <form id="form" enctype="multipart/form-data">
		<input type="file" name="file1" />    	
		<input type="button" value="提交" onclick="UpladFile()"/>
    </form>
    
</body>
</html>