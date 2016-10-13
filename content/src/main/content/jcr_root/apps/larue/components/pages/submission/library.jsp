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
%><%@page import="java.util.Collections,
				  java.util.Comparator,
				  java.util.Iterator,
                  java.util.List,
				  java.util.LinkedList,
				  org.apache.sling.api.resource.ResourceResolver,
				  org.apache.sling.api.resource.ResourceUtil,
                  org.apache.sling.api.resource.ValueMap,
                  com.day.cq.wcm.webservicesupport.Configuration,
				  com.day.cq.wcm.webservicesupport.ConfigurationManager,
				  com.day.cq.wcm.webservicesupport.ServiceConstants,
				  com.day.cq.wcm.webservicesupport.Service,
                  com.day.cq.wcm.webservicesupport.ServiceLibFinder"
%>
<%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
<cq:includeClientLib categories="guide.theme.survey,coralui2"/>
<script>
    function isInPublishMode() {
        var isPublish = true;
        if(!!window.CQ && !!window.CQ.WCM) {
            if (window.CQ.WCM.isEditMode() || window.CQ.WCM.isDesignMode()){
                isPublish = false;
            }
		}
        return isPublish;
    }
</script>