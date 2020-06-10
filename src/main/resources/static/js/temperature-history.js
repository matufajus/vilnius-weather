$(document).ready(function() {
	renderDailyChart();
});

$(".card a").click(function(e){
	e.preventDefault();
	$(".card a.active").removeClass("active");
	$(this).addClass("active");
	switch($(this).attr("href")) {
	  case "daily":
	    renderDailyChart();
	    break;
	  case "weekly":
		renderWeeklyChart();
	    break;
	  case "monthly":
		renderMonthlyChart();
	    break;
	  default:
		renderDailyChart();
	}
});

function renderDailyChart() { 
	var dps = [];
	var chart = new CanvasJS.Chart("chartContainer", {
		theme: "light2",
		animationEnabled: true,
		title: {
			text: "Daily"
		},
		axisX: {
			valueFormatString: "HH:mm",
			interval: 1,
		    intervalType: "hour"
		},
		axisY: {
			title: "Temperature (in \xB0C)",
			suffix: " °C"
		},
		data: [{
			type: "line",
			xValueType: "dateTime",
			xValueFormatString: "DD DDD HH:mm ",
			yValueFormatString: "## \xB0C",
			dataPoints: dps
		}]
	});
	
	$.get("/temperature/last/day", function(temperatures){
		let start = new Date(temperatures[0].observationTime).toLocaleDateString();
		let end = new Date(temperatures[temperatures.length-1].observationTime).toLocaleDateString();
		chart.options.title.text = "From " + start + " To " + end;
		$.each(temperatures, function(i, temperature){
	        let xValue = Date.parse(temperature.observationTime);
			let yValue = parseInt(temperature.value);
			dps.push({
				x : xValue,
				y : yValue
			});			
	    });
		chart.render();
	});	
}

function renderWeeklyChart(){
	var dps = [];
	var chart = new CanvasJS.Chart("chartContainer", {
		theme: "light2",
		animationEnabled: true,
		title: {
			text: "Weekly"
		},
		axisX: {
			valueFormatString: "MM-DD",
			interval: 1,
		    intervalType: "day"
		},
		axisY: {
			title: "Temperature (in \xB0C)",
			suffix: " °C"
		},
		data: [{
			type: "line",
			xValueType: "dateTime",
			xValueFormatString: "YYYY-MM-DD HH:00 ",
			yValueFormatString: "## \xB0C",
			dataPoints: dps
		}]
	});
	
	$.get("/temperature/last/week", function(temperatures){	
		let start = new Date(temperatures[0].observationTime).toLocaleDateString();
		let end = new Date(temperatures[temperatures.length-1].observationTime).toLocaleDateString();
		chart.options.title.text = "From " + start + " To " + end;
		$.each(temperatures, function(i, temperature){
	        let xValue = Date.parse(temperature.observationTime);
			let yValue = parseInt(temperature.value);
			dps.push({
				x : xValue,
				y : yValue
			});			
	    });
		chart.render();
	});	
}

function renderMonthlyChart(){
	var dps = [];
	var chart = new CanvasJS.Chart("chartContainer", {
		theme: "light2",
		animationEnabled: true,
		title: {
			text: "Monthly"
		},
		axisX: {
			valueFormatString: "MM-DD",
			interval: 2,
		    intervalType: "day"
		},
		axisY: {
			title: "Temperature (in \xB0C)",
			suffix: " °C"
		},
		data: [{
			type: "line",
			xValueType: "dateTime",
			xValueFormatString: "YYYY-MM-DD ",
			yValueFormatString: "## \xB0C",
			dataPoints: dps
		}]
	});
	
	$.get("/temperature/last/month", function(temperatures){
		let start = new Date(temperatures[0].observationTime).toLocaleDateString();
		let end = new Date(temperatures[temperatures.length-1].observationTime).toLocaleDateString();
		chart.options.title.text = "From " + start + " To " + end;
		$.each(temperatures, function(i, temperature){
	        let xValue = Date.parse(temperature.observationTime);
			let yValue = parseInt(temperature.value);
			dps.push({
				x : xValue,
				y : yValue
			});			
	    });
		chart.render();
	});	
}


