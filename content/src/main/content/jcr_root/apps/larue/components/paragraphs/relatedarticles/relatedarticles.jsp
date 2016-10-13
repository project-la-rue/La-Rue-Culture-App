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
<%@page session="false"
import = "org.osgi.service.cm.Configuration,
    java.util.Dictionary,
    java.lang.String,
	com.day.cq.wcm.api.WCMMode,
	com.day.cq.wcm.foundation.Placeholder,
    org.osgi.service.cm.ConfigurationAdmin"
%>
<%
ConfigurationAdmin configurationAdmin = sling.getService(org.osgi.service.cm.ConfigurationAdmin.class);
Configuration conf = configurationAdmin.getConfiguration("com.adobe.larue.impl.workflows.ArticleCreationStep");
Dictionary<String, Object> confProps = null;
String configServerUrl = "";
if (conf != null) {
	confProps = conf.getProperties();
    if(confProps != null){
        configServerUrl = confProps.get("serverurl").toString();
    }
}
String url = configServerUrl.isEmpty() ? properties.get("url", "") : configServerUrl;
String collection = properties.get("collection", "");
String article = properties.get("article", "");
Integer numOfRecords = properties.get("numOfRecords", pageProperties.get("numOfRecords", 0));
String errorMsg = properties.get("errorMessage", pageProperties.get("errorMessage", "NO RELATED ARTICLES ARE CURRENTLY AVAILABLE"));
Boolean newsfeedOnly = properties.get("newsfeedOnly", pageProperties.get("newsfeedOnly", false));
Boolean enabled = properties.get("enabled", pageProperties.get("enabled", false));
%>
<div id="larue-related-articles"></div>
<cq:includeClientLib categories="larue.relatedarticles"/>

<script>
	$(function() {

        function onSuccess(data) {
			var articles = data.articles;
			var relatedArticlesContent = "";
			for (var i = 0; i < articles.length; i++) {
				var article = articles[i];
				relatedArticlesContent += "<a class='larue-related-article' href='" + article.navto + "'>";
				relatedArticlesContent += "<div class='bg-container'><img class='larue-related-article-image' src='" + article.thumbnail + "'></div>";
				relatedArticlesContent += "<p class='title'>" + article.title + "</p>";
				relatedArticlesContent += "<p class='more'>READ FULL ARTICLE<span class='arrow'>&#8594;</span></p>";
				relatedArticlesContent += "</a>";
			}
			$("div#larue-related-articles").html(relatedArticlesContent);
            $(document).trigger("relatedArticlesUpdated", data);
        }

        function onError(errorMessage) {
            var relatedArticlesContent = "";
            relatedArticlesContent += "<a class='larue-related-article'>";
            relatedArticlesContent += "<p class='error'>" + errorMessage +  "</p>";
            relatedArticlesContent += "</a>";
			$("div#larue-related-articles").html(relatedArticlesContent);
		}

        var options = {};
		var url = "<%= url %>";
		var collection = "<%= collection %>";
		var article = "<%= article %>";
        var errorMessage = "<%= errorMsg %>";
        var numOfRecords = <%= numOfRecords %>;
        var newsfeedOnly = <%= newsfeedOnly %>;
        var enabled = <%= enabled %>;
        if (!!collection) {
			options.collection = collection;
        }
        if (!!article) {
			options.article = article;
        }
        if (!!numOfRecords && numOfRecords > 0) {
			options.numOfRecords = numOfRecords;
        }
        if (!!newsfeedOnly) {
			options.newsfeedOnly = newsfeedOnly;
        }

        if (enabled == true) {
            getRelatedArticles(url, options, onSuccess, function(error){
                console.error(error);
                onError(errorMessage);
            });
        }
	});
</script>
<c:set var="wcmMode"><%= WCMMode.fromRequest(request) != WCMMode.DISABLED %></c:set>
<c:set var="enabled"><%= enabled %></c:set>
<c:if test="${enabled == false && wcmMode == true}">
<%
final String placeholder = Placeholder.getDefaultPlaceholder(slingRequest, component, null);
%>
<%= placeholder %>
</c:if>