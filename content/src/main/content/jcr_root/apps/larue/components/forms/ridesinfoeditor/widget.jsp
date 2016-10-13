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
%><%--
  DropDown List Component
--%>
<%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
<%-- todo: In case of repeatable panels, please change this logic at view layer --%>

<div style="background-color: #DEDEDE;  margin-bottom: 50px; padding: 10px;">

    <div class="row">
        <div id="rideRows" class="col-md-5">
    
            <div class="row">
                <div class="row col-md-12" style="margin-top: 20px;">
                    <div class="col-md-3">
                        <label style="width:100%;">Source name:</label>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <input style="width:100%;" id="rideSourceName" placeholder="Enter ride name from source (csv)..."/>
                    </div>
                </div>

                <div class="row col-md-12" style="margin-top: 20px;">
                    <div class="col-md-3">
                        <label style="width:100%;">Ride date:</label>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
    					<input style="width:100%;" id="rideDate" placeholder="2016-04-20"/>
                    </div>
                </div>
                 <div class="row col-md-12" style="margin-top: 20px; margin-bottom: 20px;">
                    <div class="col-md-3">
                        <label style="width:100%;">Ride time:</label>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <input style="width:100%;" id="rideTime" placeholder="8:00am - 9:00am"/>
                    </div>
                </div>
    
                <div class="row" style="margin-top: 40px;">
                    <div class="col-md-3">
                        <label style="width:100%;">Route:</label>
                    </div>
    
                    <div class="col-md-8 col-md-offset-1">
                        <div class="dropdown">
                          <button class="btn btn-default dropdown-toggle"
                                                style="width:100%;"
                                                type="button" id="dropdownMenu1"
                                                data-toggle="dropdown"
                                                aria-haspopup="true"
                                                aria-expanded="true">
                            Select a route <span class="caret"></span>
                          </button>
                          <ul id="routeListDropdown" class="dropdown-menu" aria-labelledby="dropdownMenu1" style="width:100%;" data-path>
                            <li><a href="#">No routes avaialble</a></li>
                          </ul>
                        </div>
                    </div>
                </div>
    
                <div class="row col-md-12">
                    <button id="addRide" type="button" class="btn btn-default" aria-label="add ride">
                      <span class="coral-Icon coral-Icon--sizeM coral-Icon--addCircle" aria-hidden="true"></span>
                    </button>
                    <button id="removeRide" type="button" class="btn btn-default" aria-label="remove ride">
                      <span class="coral-Icon coral-Icon--sizeM coral-Icon--delete" aria-hidden="true"></span>
                    </button>
                </div>
            </div>

        </div>
    
        <div class="col-md-offset-1 col-md-6">
            <div id="map">--->Route Map<---</div>
        </div>
        <script>
            $.fn.stravaRouteList = function(map, accessToken, athleteId) {
                var dropdownEl = this;
                getRoute(gotRoute, onRouteError);

                function getRoute(onSuccess, onError) {
                    var routeBaseURL = "https://www.strava.com/api/v3/athletes/";
                    var routeURL = routeBaseURL + athleteId + "/routes?access_token=" + accessToken;
                    var routeReq = {url: routeURL, type: "GET", dataType: "jsonp", jsonp: "callback", success: onSuccess, error: onError};
                    $.ajax(routeReq);
                }
    
                function getRides(routeData, onSuccess, onError) {
                    var ridesBaseURL = "https://www.strava.com/api/v3/athlete/activities";
                    var ridesURL = ridesBaseURL + "?access_token=" + accessToken;
                    var ridesReq = {url: ridesURL, type: "GET", dataType: "jsonp", jsonp: "callback", success: onSuccess, error: onError};
                    $.ajax(ridesReq);
                }
    
                function onRouteError(error) {
                    console.log("Cannot get routes -");
                    console.log(error);
                }
            
                function gotRoute(data) {
                    dropdownEl.empty();
                    var routeData = data.map(function(e){
                        return {label: e.name, value: e.map.summary_polyline};
                    })
                    .map(function(item){
                        return {sDisplayVal: item.label, sSaveVal: item.value};
                    });
                    getRides(routeData, function(rides){
                        gotRides(rides, routeData);
                    }, onRidesError);
                }
    
                function onRidesError(error) {
                    console.log("Cannot get rides -");
                    console.log(error);
                }
            
                function gotRides(rides, routeData) {
                    var ridesData = rides.map(function(e){
                        return {label: e.name, value: e.map.summary_polyline};
                    })
                    .map(function(item){
                         return {sDisplayVal: item.label, sSaveVal: item.value};
                    });
                    // Concat
                    Array.prototype.push.apply(routeData, ridesData);
            
                    // populate
                    routeData.forEach(function(item){
                        var li = $("<li/>");
                        var a = $("<a/>", {href: "javascript:void(0);", text: item.sDisplayVal, "data-path": item.sSaveVal});
                        li.append(a);
                        dropdownEl.append(li);
                    });
    
                    if (routeData instanceof Array && routeData.length > 0) {
                        var defaultVal = routeData[0].sSaveVal;
                        dropdownEl.attr("data-path", defaultVal);
                        map.addEncodedPath(defaultVal, true);
                    }
                    bindToMap(window.map);
                };
        
                function bindToMap(map) {
                   if (!map) return;
                   dropdownEl.find('li a').on('click', function(){
                        var path = $(this).attr("data-path");
                        dropdownEl.attr("data-path", path);
                        map.addEncodedPath(path, true);
                   });
                }
            };
        </script>
        <script>
            (function(scope){
                if (!!window.larue && !!window.larue.Map) {
                    var mapEl = $("div#map");
                    var h = mapEl.parent().css("height");
                    mapEl.css("height","250px");
                    scope.map = new larue.Map($("div#map")[0], 15);

                    var accessToken = "caaf3eb55adb7bfc0315bb1cc11b190284697327";
                    var athleteId = "15028083";
                    $("#routeListDropdown").stravaRouteList(map, accessToken, athleteId);
                }
            })(window);
        </script>
    </div>
	<div class="row" style="margin-top: 25px;">
         <table id="ridesInfoList" class="coral-Table coral-Table--hover">
            <thead style="display:none;">
                <tr class="coral-Table-row">
                <th class="coral-Table-headerCell">Source Name</th>
				<th class="coral-Table-headerCell">Date</th>
                <th class="coral-Table-headerCell">Ride Time</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div class="<%= GuideConstants.GUIDE_FIELD_WIDGET%>
        style="${guide:encodeForHtmlAttr(guideField.styles,xssAPI)};">
	<input type="hidden" id="${guideid}${'_widget'}" name="${guide:encodeForHtmlAttr(guideField.name,xssAPI)}" value="${guide:encodeForHtmlAttr(guideField.value,xssAPI)}" style="${guide:encodeForHtmlAttr(guideField.widgetInlineStyles,xssAPI)}" placeholder="${guide:encodeForHtmlAttr(guideField.placeholderText,xssAPI)}"/>
</div>
<%-- End of Widget Div --%>