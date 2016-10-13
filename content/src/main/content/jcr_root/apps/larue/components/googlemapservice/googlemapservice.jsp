<%------------------------------------------------------------------------
 ~
 ~ Copyright 2016 Adobe Systems Incorporated. All rights reserved.
 ~ 
 ~ This file is licensed to you under the Apache License, Version 2.0 (the "License"); 
 ~ you may not use this file except in compliance with the License. You may obtain a copy
 ~ of the License at http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software distributed under
 ~ the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS 
 ~ OF ANY KIND, either express or implied. See the License for the specific language
 ~ governing permissions and limitations under the License.
 ~
 --------------------------------------------------------------------------%><%
%><%@page import="org.apache.sling.api.resource.Resource,
                org.apache.sling.api.resource.ValueMap,
                org.apache.sling.api.resource.ResourceUtil,
                com.day.cq.wcm.webservicesupport.Configuration,
                com.day.cq.wcm.webservicesupport.ConfigurationManager" %>
<%@include file="/libs/foundation/global.jsp" %><%

String[] services = pageProperties.getInherited("cq:cloudserviceconfigs", new String[]{});
ConfigurationManager cfgMgr = resource.getResourceResolver().adaptTo(ConfigurationManager.class);
if(cfgMgr != null) {
    String apiKey = null;
    Configuration cfg = cfgMgr.getConfiguration("googlemapservice", services);
    if(cfg != null) {
        apiKey = cfg.get("apiKey", null);
    }

    if(apiKey != null) {
    %>
<script src="https://maps.googleapis.com/maps/api/js?key=<%=apiKey%>&libraries=geometry"></script>
<script type="text/javascript">
var larue = {

    Map: function(mapEle, zoomLevel) {

      var _options = {
        zoom: zoomLevel||12,
        center: {lat: 34.366, lng: -89.519}
      };

      if (mapEle instanceof Element) {
    	el = mapEle;
	  } else {
		el = document.getElementById(mapEle);
	  }

      var _map = new google.maps.Map(el, _options);

      var _poly = new google.maps.Polyline({
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 3,
        map: _map
      });

      var _geocoder = new google.maps.Geocoder();

      this.centerAtAddress = function(address, onSuccess, onError) {
        _geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            var loc = results[0].geometry.location;
            _map.setCenter(loc);
            var marker = new google.maps.Marker({
                map: _map,
                position: loc
            });
              if (onSuccess) {
                  onSuccess(loc);
              } 
          } else {
            var error = "Geocode was not successful for the following reason: " + status;
            if (onError) onError(error);
          }
        });
     };

     this.fitBounds = function(startLat, startLng, endLat, endLng) {
       var bounds = new google.maps.LatLngBounds(
         new google.maps.LatLng(startLat, startLng),
         new google.maps.LatLng(endLat, endLng)
       );
       _map.fitBounds(bounds);
     };

     this.fitBoundsByEncodedPath = function(encodedPath){
         var bounds = new google.maps.LatLngBounds();
         var path = google.maps.geometry.encoding.decodePath(encodedPath);
         path.forEach(function(e){
             bounds.extend(e);
         });
         _map.fitBounds(bounds);
         _map.setCenter(bounds.getCenter());
         _map.panToBounds(bounds);
     };

     this.addEncodedPath = function(encodedPath, autoFit) {
         _poly.setPath(google.maps.geometry.encoding.decodePath(encodedPath));
         if (autoFit) {
             this.fitBoundsByEncodedPath(encodedPath);
         }
     };
        
     this.setZoom = function(zoomLevel) {
       _map.setZoom(zoomLevel);  
     };
    }
    
};
</script><%
    }
}
%>
