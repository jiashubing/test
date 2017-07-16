//查询城市天气
$("#weatherBtn").click(function(){
	var cityName = $("#selCity").val();
	if(cityName==""){
		alert("发生异常，未选择城市");
		return false;
	}
	$.ajax({
		url:"pcweather",
		type:"post",
		data:{"cityName":cityName},
		dataType:"json",
		success:function(result){
			if(result.status==1) {
				weatherTable
				$("#weatherTable").css("display","block");
				$("#weather_province").html(result.body.province);
				$("#weather_city").html(result.body.city);
				$("#weather_cityCode").html(result.body.cityCode);
				$("#weather_lastUpdateDate").html(result.body.lastUpdateDate);
				$("#weather_todayTemperature").html(result.body.todayTemperature);
				$("#weather_todaySituation").html(result.body.todaySituation);
				$("#weather_todayWind").html(result.body.todayWind);
				$("#weather_todayActual").html(result.body.todayActual);
				$("#weather_todayLife").html(result.body.todayLife);
				$("#weather_secondTemperature").html(result.body.secondTemperature);
				$("#weather_secondSituation").html(result.body.secondSituation);
				$("#weather_secondWind").html(result.body.secondWind);
				$("#weather_thirdTemperature").html(result.body.thirdTemperature);
				$("#weather_thirdSituation").html(result.body.thirdSituation);
				$("#weather_thirdWind").html(result.body.thirdWind);
				$("#weather_cityIntroduce").html(result.body.cityIntroduce);
			}
		},
		error: function () {
			
		}
	});
	
});