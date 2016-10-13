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
%><%@page session="false"%><%--
  Copyright 1997-2009 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================



--%><%@page contentType="text/html"
            pageEncoding="utf-8"
            import="javax.jcr.Node,
                    java.util.Iterator,
                    com.day.cq.wcm.webservicesupport.Configuration,
                    com.day.cq.wcm.webservicesupport.Service,
                    org.apache.commons.lang.StringEscapeUtils,
                    org.apache.sling.api.resource.Resource"%><%
%><%@include file="/libs/foundation/global.jsp"%>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/init.jsp"%>
<%
    String id = currentPage.getName();
    String title = xssAPI.encodeForHTML(properties.get("jcr:title", id)); 
    String description = xssAPI.encodeForHTML(properties.get("jcr:description", ""));
    

    String path = resource.getPath();
    String resourceType = resource.getResourceType();
    String dialogPath = resource.getResourceResolver().getResource(resourceType).getPath() + "/dialog";  
     
%><body>
    <div><cq:include path="trail" resourceType="cq/cloudserviceconfigs/components/trail"/></div>
    <p class="cq-clear-for-ie7"></p>
    <h1><%= title %></h1>
    <p><%= description %></p>
    <div>
    <script type="text/javascript">
        CQ.WCM.edit({
            "path":"<%= path %>",
            "dialog":"<%= dialogPath %>",
            "type":"<%= resourceType %>",
            "editConfig":{
                "xtype":"editbar",
                "listeners":{
                    "afteredit":"REFRESH_PAGE"
                },
                "inlineEditing":CQ.wcm.EditBase.INLINE_MODE_NEVER,
                "disableTargeting": true,
                "actions":[
                    CQ.I18n.getMessage("Configuration"),
                    {
                        "xtype": "tbseparator"
                    },
                    CQ.wcm.EditBase.EDIT
                    <%
                    if (serviceUrl != null) {
                    %>
                    ,    
                    {
                        "xtype": "tbseparator"
                    },                
                    {
                        "xtype": "tbtext", 
                        "text": "<a href='<%=serviceUrl%>' target='_blank' style='color: #15428B; cursor: pointer; text-decoration: underline'>" + CQ.I18n.getMessage("<%=serviceUrlLabel%>") + "</span>"
                    }
                    <%
                    }
                    %>
                ]
            }
        });
        </script>     
    </div>
    <cq:include script="content.jsp" />
    <cq:include script="opendialog.jsp" />
    <p>&nbsp;</p>
</body>
