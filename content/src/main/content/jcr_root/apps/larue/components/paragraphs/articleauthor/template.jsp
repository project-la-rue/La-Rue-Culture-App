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
%></%><%@ page import="java.util.UUID,
    com.day.cq.commons.Doctype,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.foundation.Image,
    com.day.cq.wcm.foundation.Placeholder" %><%
%><%@include file="/libs/foundation/global.jsp"%><%
    String imgId = "author-image-" + UUID.randomUUID().toString();
    String authorName = properties.get("title", "");
    Image image = new Image(resource);
    image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(slingRequest));

    //drop target css class = dd prefix + name of the drop target in the edit config
    image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
    image.loadStyleData(currentStyle);
    image.setSelector(".img"); // use image script
    image.setDoctype(Doctype.fromRequest(request));

    if (!currentDesign.equals(resourceDesign)) {
        image.setSuffix(currentDesign.getId());
    }
    %>
    
<div class="larue-article-author">
    <div class="image-container">
        <% image.draw(out); %>
    </div>
    <div id='<%=imgId %>' class="image author-avatar">
        <div id='hex1'></div>
        <div id='hex2'></div>
        <div id='hex3'></div>
    </div>
    <span class="author-name">By <% 
        if( authorName == null || authorName.length() == 0 ) {%>
        <cq:text property="text" tagName="span" escapeXml="true" placeholder=""/>
        <% } else { %><%= authorName %></%><% } %>
    </span>
</div>