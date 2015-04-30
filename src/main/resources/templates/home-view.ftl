<html>
<head>
	<title>home</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

<#--leaflet-->
<#--
	<link rel="stylesheet" href="/js/node_modules/leaflet/dist/leaflet.css"/>
	<script src="/js/node_modules/leaflet/dist/leaflet.js"></script>
-->

<#--mapbox-->
	<link href='https://api.tiles.mapbox.com/mapbox.js/v2.1.9/mapbox.css' rel='stylesheet'/>
	<script src='https://api.tiles.mapbox.com/mapbox.js/v2.1.9/mapbox.js'></script>

	<style>
		body {
			margin: 0;
			padding: 0;
		}

		#map {
			width: 100%;
			height: 500px;
		}
	</style>

</head>
<body>

<div id="map"></div>

<script>
	// with mapbox
	L.mapbox.accessToken = 'pk.eyJ1IjoibWFwYm94IiwiYSI6IlhHVkZmaW8ifQ.hAMX5hSW-QnTeRCMAy9A8Q';
	//L.mapbox.accessToken = 'pk.eyJ1IjoidHNjaGFjaHQiLCJhIjoiemU2eWRycyJ9.uXu1nMMiV7qFQnevRpYClA';
	var map = L.mapbox.map('map', 'tschacht.d9dd98d8').setView([52.5, 13.5], 13);
	L.mapbox.tileLayer('tschacht.d9dd98d8').addTo(map);

	// with plain leaflet
	/*
		var map = L.map('map').setView([52.5, 13.5], 13);
		L.tileLayer('https://{s}.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token={token}', {
			token: 'pk.eyJ1IjoibWFwYm94IiwiYSI6IlhHVkZmaW8ifQ.hAMX5hSW-QnTeRCMAy9A8Q',
			mapId: 'tschacht.d9dd98d8',
			maxZoom: 18
		}).addTo(map);
	*/

	// add a marker
	var marker = L.marker([52.5, 13.5]).addTo(map);

	// add a circle
	var circle = L.circle([52.5, 13.5], 700, {
		color: 'red',
		fillColor: '#f03',
		fillOpacity: 0.5
	}).addTo(map);

	// add a polygon
	var polygon = L.polygon([
		[52.5, 13.5],
		[52.49, 13.55],
		[52.51, 13.55]
	]).addTo(map);

	// add popups
	marker.bindPopup("<b>Hello world!</b><br>I am a popup.").openPopup();
	circle.bindPopup("I am a circle.");
	polygon.bindPopup("I am a polygon.");
</script>

<h1>hello form templates/home-view.ftl</h1>
</body>
</html>
