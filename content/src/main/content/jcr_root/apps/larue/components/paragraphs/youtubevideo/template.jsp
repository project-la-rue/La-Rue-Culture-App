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
        import="java.util.regex.Matcher,
                java.util.regex.Pattern"%>
<%
String base64image = properties.get("thumbnail", "");
String videoUrl = getYoutubeVideoThumbnailUrl(properties.get("videoUrl", ""));
%>
<div class="larue-youtube-video">
    <div class="offline-overlay"><div class="video-unavailable">Video not available because no connection</div></div>
    <a href="<%=videoUrl%>">
        <span></span>
        <img src="<%=base64image%>" />
    </a>
</div>
<%!
String YOUTUBE_VIDEO_ID_REGEX = "^.*(youtu.be\\/|v\\/|u\\/\\w/|embed\\/|watch\\?v=|\\&v=)([^#\\&\\?]*).*";

String getYoutubeVideoThumbnailUrl(String youtubeUrl) {
    String id = getYoutubeVideoId(youtubeUrl);
    return String.format("https://www.youtube.com/embed/%s?autoplay=%s", id, 1);
}

String getYoutubeVideoId(String youtubeUrl) {
    String id = "";
    Matcher matcher = Pattern.compile(YOUTUBE_VIDEO_ID_REGEX).matcher(youtubeUrl);
    while (matcher.find()) {
        if (matcher.group(2).length() == 11) {
            id = matcher.group(2);
            break;
        }
    }
    return id;
}
%>
