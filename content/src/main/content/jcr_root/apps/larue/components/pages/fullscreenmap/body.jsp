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
%><%@include file="/libs/foundation/global.jsp"%>
<cq:include path="cloudservices" resourceType="cq/cloudserviceconfigs/components/servicecomponents"/>
<%@page session="false" import="com.day.cq.wcm.api.Page" %>
<style>
    html, body, #map {
        width: 100%;
        height: 100%;
    }
</style>
<div id="map"></div>
<script>
(function(){
    if (larue && larue.Map) {
        var queryStr = location.search.substr(1);
        var paramPairs = queryStr.split("&");
        var params = paramPairs.reduce(function(prev, current){
            var kv = current.split("=");
			prev[kv[0]] = kv[1];
            return prev;
        }, {});

    	var map = new larue.Map("map", 12);
		var path = params["pathdata"];
        
		if (path) {
            path = unescape(unescape(path));
            map.addEncodedPath(path, true);
        }
    }
})();
</script>