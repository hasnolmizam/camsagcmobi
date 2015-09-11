/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
        document.addEventListener("online", onOnline, false);
        document.addEventListener("resume", onResume, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicity call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
        alert("onDeviceReady");
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        
        console.log('Received Event: ' + id);
        alert("receivedEvent");
        if(isMobile.any()) {
        	
	        if(googleLibExists()){
	        	alert("googleLibExists");	
	        	initialize();
	        }
	        else{
	        	alert("loadMapsApi");	
	        	loadMapsApi();
	        }
	        
        }
        
    }
};

var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows();
    }
};

function googleLibExists(){
	return typeof(google) != "undefined" && google.maps;
}

function loadAsynchronousScript() {
  var script = document.createElement('script');
  script.type = 'text/javascript';
  script.src = 'http://maps.googleapis.com/maps/api/js?key=AIzaSyCr0wsx4a5_o03hPTpC_CtRARjzCnOEGX4&sensor=false&libraries=places&callback=initialize';
  document.body.appendChild(script);
}


function loadMapsApi () {
    if(navigator.connection.type === Connection.NONE) {
		  alert('google maps library not loaded');
        return;
    }
    if(!googleLibExists()){
	    loadAsynchronousScript();
    }
}

function onOnline () {
    loadMapsApi();
}

function onResume () {
    loadMapsApi();
}

var directionsDisplay;
var map;

function initialize() {
	
	console.log('map init');
	
	 var styles = [{"featureType":"water","elementType":"all","stylers":[{"hue":"#76aee3"},{"saturation":38},{"lightness":-11},{"visibility":"on"}]},{"featureType":"road.highway","elementType":"all","stylers":[{"hue":"#8dc749"},{"saturation":-47},{"lightness":-17},{"visibility":"on"}]},{"featureType":"poi.park","elementType":"all","stylers":[{"hue":"#c6e3a4"},{"saturation":17},{"lightness":-2},{"visibility":"on"}]},{"featureType":"road.arterial","elementType":"all","stylers":[{"hue":"#cccccc"},{"saturation":-100},{"lightness":13},{"visibility":"on"}]},{"featureType":"administrative.land_parcel","elementType":"all","stylers":[{"hue":"#5f5855"},{"saturation":6},{"lightness":-31},{"visibility":"on"}]},{"featureType":"road.local","elementType":"all","stylers":[{"hue":"#ffffff"},{"saturation":-100},{"lightness":100},{"visibility":"simplified"}]},{"featureType":"water","elementType":"all","stylers":[]}];
	 var styledMap = new google.maps.StyledMapType(styles, {name: ""});
	
	  directionsDisplay = new google.maps.DirectionsRenderer({polylineOptions: {
	      strokeColor: "red"
	    }});
	  var mapOptions = {
	          center: new google.maps.LatLng(47.6826215,13.0984208,17),
	          zoom: 15,
	          disableDefaultUI: true,
	          mapTypeControlOptions: {
	            mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style']
	            }
	        };
	  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	        map.mapTypes.set('map_style', styledMap);
	        map.setMapTypeId('map_style');
	        map.setOptions({styles: styles});
	
	    var defaultBounds = new google.maps.LatLngBounds(
	          new google.maps.LatLng(47.67052,13.114028),
	          new google.maps.LatLng(47.6910273,13.1153865));
	
	        var options = {
	          bounds: defaultBounds,
	        };
	
	        var start_input = document.getElementById('start');
	        start_autocomplete = new google.maps.places.Autocomplete(start_input, options);
	    var end_input = document.getElementById('end');
	        end_autocomplete = new google.maps.places.Autocomplete(end_input, options);
	
	  alert('here');      
	  directionsDisplay.setMap(map);
}