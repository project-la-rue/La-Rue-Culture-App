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

--%><%--
    Open configuration dialog if the property designated by "connectedWhen" is filled in.
    Derived components may override this to change the opening condition.
    --%>
<%@include file="/libs/foundation/global.jsp"%>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/init.jsp"%>
    <script type="text/javascript">
<% if (properties.get("apiKey","").trim().equals("")) { %>
        CQ.WCM.onEditableReady("<%= resource.getPath() %>", function(editable){
            CQ.wcm.EditBase.showDialog(editable);
        }, this);
<% } else {%>
        $CQ(".when-config-successful").show();
<% } %>
    </script>
