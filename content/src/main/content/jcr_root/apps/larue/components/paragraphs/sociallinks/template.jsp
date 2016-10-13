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
%><%@page session="false"
        import="com.day.cq.rewriter.linkchecker.LinkCheckerSettings"%><%@include file="/libs/foundation/global.jsp"%>
<%   
   LinkCheckerSettings.fromRequest(slingRequest).setIgnoreExternals(true);
   String linkedin = properties.get("linkedin","");
   String facebook  = properties.get("facebook","");
   String twitter = properties.get("twitter","");
   String instagram = properties.get("instagram","");
   String email  = properties.get("email","");
%>
<c:set var="hasLinkedin"><%= !linkedin.isEmpty() %></c:set>
<c:set var="hasFacebook"><%= !facebook.isEmpty() %></c:set>
<c:set var="hasTwitter"><%= !twitter.isEmpty() %></c:set>
<c:set var="hasInstagram"><%= !instagram.isEmpty() %></c:set>
<c:set var="hasEmail"><%= !email.isEmpty() %></c:set>
<div class="larue-social-links" >
    <div class="container">
        <c:if test="${hasFacebook}">
            <a class="facebook" href="<%= facebook %>"></a>
        </c:if>
        <c:if test="${hasLinkedin}">
            <a class="linkedin" href="<%= linkedin %>"></a>
        </c:if>
        <c:if test="${hasTwitter}">
            <a class="twitter" href="<%= twitter %>"></a>
        </c:if>
		<c:if test="${hasInstagram}">
            <a class="instagram" href="<%= instagram %>"></a>
        </c:if>        
        <c:if test="${hasEmail}">
            <a class="email" href="mailto:<%= email %>"></a>
        </c:if>
    </div>
</div>