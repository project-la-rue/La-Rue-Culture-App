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
%><%@ page session="false" %>
<%@include file="/libs/foundation/global.jsp" %><% 
String rideschedule = "";
String rideseditorbox = "";
Page myPage = currentPage; //reference to whatever page contains the component                                             
Resource myResource = myPage.getContentResource();
ValueMap resourceProperties = myResource.getValueMap();
rideschedule = resourceProperties.get("rideschedule","").toString();
rideseditorbox = resourceProperties.get("rideseditorbox","").toString();
String eventRegURL = resourceProperties.get("eventRegURL", "").toString();
%>
<cq:includeClientLib categories="larue.components" />
<div id="ride-schedule">
    <div id="ride-schedule-header">Ride Schedule</div>
    <div id="bring-bike"></div><div class="bike-toggle" id="bike-button"><button></button></div>
</div>
    
<script>
    (function(){
		var isLocal = (location.protocol.indexOf("file") == 0);
        var rideScheduleURL = '<%= rideschedule %>';
        var rideseditorbox = '<%= rideseditorbox %>';
		var eventRegURL = encodeURI('<%= eventRegURL %>');
        if( window.localStorage.getItem("own_bikeYN") == "yes" ) {
            $('#bike-button').toggleClass("bike-toggle-selected");
            $('#bring-bike').text("I'm bringing my own bike");
        }
        else {
            $('#bring-bike').text("I need to borrow a bike");
        }
        
        if (isLocal) {
            var isIOS = /(iPhone|iPod|iPad)/i.test(navigator.userAgent);
            var localUrl = "";
            if (isIOS) {
                localUrl = location.href.substring(0, location.href.lastIndexOf("article"))  + 'HTMLResources' + rideScheduleURL;
            } else {
                localUrl = '../HTMLResources' + rideScheduleURL;
            }
            document.addEventListener("deviceready", onDeviceReady, false);
            function onDeviceReady() {
                renderRideSchedule(eventRegURL, localUrl, rideseditorbox, isIOS );
                document.removeEventListener("deviceready", onDeviceReady );
            }
        } else {
			renderRideSchedule(eventRegURL, rideScheduleURL, rideseditorbox );
        }
        
        $(document).on('click', '.bike-toggle', toggleBikeClass );

        function toggleBikeClass() {
            $(this).toggleClass('bike-toggle-selected');
            if ( $(this).hasClass('bike-toggle-selected') ) {
                $('#bring-bike').text("I'm bringing my own bike");
                window.localStorage.setItem("own_bikeYN", "yes");
            } else {
                $('#bring-bike').text("I need to borrow a bike");
                window.localStorage.setItem("own_bikeYN", "no");
            }
        }
        
    })();
</script>

