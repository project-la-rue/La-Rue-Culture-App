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
		import="com.adobe.granite.xss.XSSAPI"%>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1, user-scalable=no"/>

    <cq:includeClientLib categories="larue.common"/>
    <cq:include script="customheadlibs.jsp"/>
    <cq:include script="headlibs.jsp"/>
    
    <title><%= xssAPI.encodeForHTML( currentPage.getTitle() == null ? currentPage.getName() : currentPage.getTitle() ) %></title>

    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <cq:include script="stats_legacy.jsp"/>
    <cq:include script="/libs/cq/cloudserviceconfigs/components/servicelibs/servicelibs.jsp"/>
    <cq:include script="/libs/wcm/core/browsermap/browsermap.jsp" />
    <cq:include script="/libs/wcm/mobile/components/simulator/simulator.jsp"/>
</head>