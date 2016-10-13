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
%><%@ page import="org.apache.sling.api.resource.Resource,
            org.apache.sling.api.resource.ValueMap"%>
<%@include file="/libs/foundation/global.jsp" %>
<%@page session="false" %>
<div class="larue-guides">
    <div class="larue-guides-container aem-Grid aem-Grid--12">
        <div class="heading">GUIDES</div>
<%
    if (currentNode.hasNode("guides")) {
        Node mapNode = currentNode.getNode("guides"), cNode;
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
                            Resource res = resource.getResourceResolver().resolve(cNode.getPath());
                            if(res != null){
                                ValueMap props = res.getValueMap();
%>
    <div class="larue-guide aem-GridColumn aem-GridColumn--default--12 aem-Grid aem-Grid--12">
        <div class="image guide-avatar aem-GridColumn aem-GridColumn--default--3">
            <img src="<%= props.get("fileReference","") %>"/>
        </div>
        <div class="aem-GridColumn aem-GridColumn--default--1">&nbsp;</div>
        <div class="guide-info aem-GridColumn aem-GridColumn--default--8">
            <div class="name"><%= props.get("guidename","") %></div>
            <div class="title"><%= props.get("guidedescription","") %></div>
        </div>
    </div>
<%
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }
%>
    </div>
</div>