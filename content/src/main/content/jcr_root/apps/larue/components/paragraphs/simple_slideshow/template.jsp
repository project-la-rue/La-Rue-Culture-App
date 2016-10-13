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
%><%@ page import="java.io.PrintWriter"
    import="com.day.cq.commons.Doctype,
            com.day.cq.wcm.api.components.DropTarget,
            com.day.cq.wcm.foundation.Placeholder,
            com.day.cq.wcm.foundation.Image,
            org.apache.sling.api.resource.Resource,
            java.text.DateFormatSymbols"%>
<%@include file="/libs/foundation/global.jsp" %>
<div class="larue-slideshow-container">
    <div class="larue-slideshow slick-simple-slideshow">
<%
    if (currentNode.hasNode("slides")) {
        Node mapNode = currentNode.getNode("slides"), cNode;
        Image image = null;
        if(mapNode != null ) {
            if(mapNode.hasNodes()){
                PropertyIterator itr = null; Property property;
                NodeIterator ni = mapNode.getNodes();
                while (ni.hasNext()) {
                    cNode = ni.nextNode();
                    itr = cNode.getProperties();
                    while(itr.hasNext()){
                        property = itr.nextProperty();
                        if(property.getName().equals("jcr:primaryType")){
                            continue;
                        }
                        if(property.getName().equals("fileReference")){
%>
        <div class="slide">
            <img src="<%= property.getString() %>"/>
        </div>
<%
                                continue;
                            }
                        }
                    }
                }
            }
        }
%>
    </div>
</div>