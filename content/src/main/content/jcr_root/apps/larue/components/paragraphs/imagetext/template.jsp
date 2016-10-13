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
%></%><%@ page import="com.day.cq.commons.Doctype,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.foundation.Image,
    org.apache.sling.api.resource.ValueMap,
    com.day.cq.wcm.foundation.Placeholder" %><%
%><%@include file="/libs/foundation/global.jsp"%><%             
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
<div class="larue-image-text">
    <div class="image"><% if(image != null && image.hasContent()){image.draw(out);} %></div>
    <div class="text">
        <div class="title"><%= pageProperties.get("dps-title","")%></div>
        <div class="subtitle"><%= pageProperties.get("dps-shortTitle","")%></div>
    </div>
</div>