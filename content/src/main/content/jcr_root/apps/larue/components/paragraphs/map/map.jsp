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
%><%@page session="false" pageEncoding="utf-8" %>
<%@include file="/libs/wcm/global.jsp"%><%
String path = properties.get("path", "");
String address = properties.get("address", "").replaceAll(" ","+");
String mapWidth = properties.get("width", "100%");
String mapHeight = properties.get("height", "435px");
Integer zoomLevel = properties.get("zoom", 12);
%>
<style>
    #map {
        width: <%= mapWidth %>;
        height: <%= mapHeight %>;
    }
</style>
<div id="map"></div>
<script>
(function(){
    if (larue && larue.Map) {
    	var map = new larue.Map("map", <%= zoomLevel %>);
		var path = "<%= path %>";
		if (path) {
            map.addEncodedPath(path, true);
        } else {
            var address = "<%= address %>";
    		map.centerAtAddress(address);
        }
    }
})();
</script>
