 $(function(){
            //转盘
            $("#inner").click(function(){
                $.ajax({
                    url:"lottery",
                    type:"post",
                    dataType:"json",
                    success:function(result){
	                    if(result.status==1){
		                    //$("#inner").unbind('click').css("cursor", "default");	//解绑语句
							var angle = parseInt(result.body); //角度 
							var msg = result.message; //提示信息
							$("#outer").rotate({ //inner内部指针转动，outer外部转盘转动
								duration : 5000, //转动时间 
								angle : 0, //开始角度 
								animateTo : 3600 + angle, //转动角度 
								easing : $.easing.easeOutSine, //动画扩展 
								callback : function() {
									var con = confirm(msg + '\n还要再来一次吗？');
									if (con) {
									} else {
										return false;
									}
								}
							});
						}
                	 },
		            error: function () {
		                alert("请求失败！");
		            }
	            });
	      });
	  });