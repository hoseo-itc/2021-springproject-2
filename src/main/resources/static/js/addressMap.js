var address = null;
var content = null;
var marker = new kakao.maps.Marker();
var infowindow = new kakao.maps.InfoWindow();
var lanlng = null;
var coord = null;
var geocoder = new kakao.maps.services.Geocoder();

$("#commit").click( e => {
	if(address == null){
		alert("주소를 선택하여 주십시오.");
		return;
	}			
	window.opener.postMessage(address);
	window.close();
});

var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(33.450701, 126.570667),
	level: 3
};

var map = new kakao.maps.Map(container, options);

// 검색 이벤트
addEventListener("keydown", event => {
	if(event.keyCode != 13) return; 
	
	geocoder.addressSearch(document.getElementById("addr").value, function(result, status) {		
         if (status === kakao.maps.services.Status.OK) {
            latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
            coord = new kakao.maps.LatLng(latlng.getLat(),latlng.getLng());
            geocoder = new kakao.maps.services.Geocoder();
            
            var callback = function(result, status) {
            	if (status === kakao.maps.services.Status.OK) {
    				var addr = result[0].address;
		        	address = addr.address_name;
		        	marker.setPosition(latlng);
	   				map.setCenter(latlng);
	   				var infowindow = new kakao.maps.InfoWindow({
	   	            	content: address
	   	        	});
	   	        	infowindow.open(map, marker);			   	        	
		        }
           };	
        }		         
        geocoder.coord2Address(coord.getLng(), coord.getLat(), callback,);
    });
      marker.setMap(map);
});

kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    latlng = mouseEvent.latLng; 
   	coord = new kakao.maps.LatLng(latlng.getLat(),latlng.getLng());
   	geocoder = new kakao.maps.services.Geocoder();
    var callback = function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var addr = result[0].address;
        address = addr.address_name;
    }
    marker.setPosition(latlng);
    infowindow.setContent(address);
    infowindow.open(map,marker);
   };
  geocoder.coord2Address(coord.getLng(), coord.getLat(), callback,);
});
marker.setMap(map);


