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
%><%@ page session="false"
           import="com.day.cq.wcm.api.WCMMode,
                   com.day.cq.wcm.foundation.Placeholder" %><%
%><%@include file="/libs/foundation/global.jsp"%><%                     
    Boolean hasContent = false;
	if (currentNode.hasNode("slides")) {
        Node mapNode = currentNode.getNode("slides");
        if(mapNode.hasNodes()){
            hasContent = true;
        }
    }
%><c:set var="wcmMode"><%= WCMMode.fromRequest(request) != WCMMode.DISABLED %></c:set><%
%><c:set var="hasContent"><%= hasContent %></c:set><%
%>
<c:choose>
    <c:when test="${!hasContent}">
        <c:if test="${wcmMode}">
<%
        	final String placeholder = Placeholder.getDefaultPlaceholder(slingRequest, component, null);
%>
            <%= placeholder %>
        </c:if>
    </c:when>
    <c:otherwise>
        <cq:include script="template.jsp"/>
    </c:otherwise>
</c:choose>