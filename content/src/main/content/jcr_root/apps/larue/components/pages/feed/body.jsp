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
<%@ page import="com.day.cq.commons.Doctype,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.foundation.Image,
    org.apache.sling.api.resource.ValueMap,
    com.day.cq.wcm.foundation.Placeholder"%>
<%@include file="/libs/foundation/global.jsp"%>


<cq:include path="weatherInfo" resourceType="/apps/larue/components/paragraphs/weatherinfo" />
<div class="larue-related-articles">
    <cq:include path="relatedarticles" resourceType="/apps/larue/components/paragraphs/relatedarticles" />
</div>	

<script>
    $(function(){
        function loadWeatherInfoForCity(cityName) {
            $.simpleWeather({
                location: cityName,
                woeid: '',
                unit: 'f',
                success: function(weather) {
                  $("#temperature").html((weather.temp+'&deg;'));
                  $("#description").html(weather.currently);
                  $("#location").html(cityName);
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