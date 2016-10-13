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
%><%@page session="false"%><%
%><%@ page import="com.day.cq.commons.Doctype,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.foundation.Image,
    org.apache.sling.api.resource.ValueMap,
    com.day.cq.wcm.foundation.Placeholder" %>
<%@include file="/libs/foundation/global.jsp"%>
<%             
    Image image = null;
    Resource res = currentPage.getContentResource();
    if(res != null){
        Resource imgres = res.getChild("image");
        if(imgres != null){
            ValueMap imgProps = imgres.getValueMap();
            String fileRef = imgProps.get("fileReference", "");
            if(fileRef != null){
                currentNode.setProperty("fileReference", fileRef);
                currentNode.getSession().save();

                image = new Image(resource);
                image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(slingRequest));
                //drop target css class = dd prefix + name of the drop target in the edit config
                image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
                image.setSelector(".img"); // use image script
                image.setDoctype(Doctype.fromRequest(request));
            }
        }
    }
%>
<div id="weatherInfo" class="larue-weatherinfo">
    <div class="image"><% if(image != null && image.hasContent()){image.draw(out);} %></div>
	<div class="feed-title text">
    	<div class="title">My Feed</div>
    </div>
    <div class="text">
		<div id="location" class="title location-name"></div>
        <div id="temperature" class="title temperature"></div>
		<div id="description" class="title description"></div>
        <div id="forecast" class="title forecast"></div>
    </div>
</div>

<script>
    $(function(){
        function loadWeatherInfoForCity(cityName) {
            $.simpleWeather({
                location: cityName,
                woeid: '',
                unit: 'f',
                success: function(weather) {
                  $("#temperature").html((weather.temp + '&deg;'));
                  $("#description").html(weather.currently);
                  $("#location").html(cityName);
                  var link = $("<a/>", {text: "VIEW FORECAST", href: weather.link});
                  link.append($("<span class='arrow'>&#8594;</span>"));
                  $("#forecast").html(link);
                },
                error: function(error) {

                }
              });
        }

        function onNewsfeedReady(data) {
            if (data instanceof Array && data.length > 0) {
                // assume the first one is the upcoming event:
                var ridesEvents = data.filter(function(a){return a.category === 'ride';});
                if (ridesEvents.length > 0) eventArticle = ridesEvents[0];
                if (eventArticle.eventCity != null) {
					loadWeatherInfoForCity(eventArticle.eventCity);
                }
            }
        }

        $(document).bind("relatedArticlesUpdated", function(evt, data){
			onNewsfeedReady(data.articles);
		});
    });
</script>