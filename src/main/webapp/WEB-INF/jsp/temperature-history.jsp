<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<title>Vilnius temperature history</title>
</head>
<body>
	<h1 class="text-center">Temperature history in Vilnius</h1>
	<div class="card text-center">
		<div class="card-header">
			<ul class="nav nav-tabs card-header-tabs">
				<li class="nav-item"><a class="nav-link active" href="daily">Daily</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="weekly">Weekly</a></li>
				<li class="nav-item"><a class="nav-link" href="monthly">Monthly</a></li>
			</ul>
		</div>
		<div class="card-body">
			<h5 class="card-title"></h5>
			<div id="chartContainer" style="height: 370px; width: 100%;"></div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.5.1.js"
		integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
		crossorigin="anonymous"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="/js/temperature-history.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>
</body>
</html>