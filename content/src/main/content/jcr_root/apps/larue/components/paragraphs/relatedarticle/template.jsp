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
        import="com.day.cq.wcm.api.PageManager,
                com.day.cq.commons.Doctype,
                com.day.cq.wcm.api.components.DropTarget,
                com.day.cq.wcm.foundation.Placeholder,
                org.apache.sling.api.resource.Resource,
                org.apache.sling.api.resource.ResourceResolver,
                com.day.cq.wcm.foundation.Image,
                org.apache.sling.api.resource.ValueMap"
%><%
    String url = properties.get("relatedlink", "");
    String hrefAttr = "";
    String title = "";
    String imgSrc = "";
    String fileRef = "";
    Image image = null;
    if (!url.equals("")) {
        String[] splitted= url.split("/");
        hrefAttr = "href=\"navto://" + splitted[splitted.length-1] + "\"";    
        final Page relatedArticle = pageManager.getPage(url);
        if(relatedArticle != null){
            Resource res = relatedArticle.getContentResource();
            if(res != null){
                ValueMap props = res.getValueMap();
                title = props.get("dps-title", "");
     
                Resource imgres = res.getChild("image");
                ValueMap imgProps = imgres.getValueMap();
                fileRef = imgProps.get("fileReference", "");
                if(fileRef != null){
                    currentNode.setProperty("fileReference", fileRef);
                    currentNode.getSession().save();
     
                    image = new Image(resource);
                    image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(slingRequest));
                    //drop target css class = dd prefix + name of the drop target in the edit config
                    image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
                    image.loadStyleData(currentStyle);
                    image.setSelector(".img"); // use image script
                    image.setDoctype(Doctype.fromRequest(request));
                    // add design information if not default (i.e. for reference paras)
                    if (!currentDesign.equals(resourceDesign)) {
                        image.setSuffix(currentDesign.getId());
                    }
                }
            }
        }
    }
%>
<a class="larue-related-article" <%=hrefAttr%>>
    <div class="bg-container">
        <%
           if(image != null ){
               image.draw(out);
           }
        %>
    </div>
    <div class="title"><%= title %></div>
    <div class="more">READ FULL ARTICLE<span class="arrow">&#8594;</span></div>
</a>