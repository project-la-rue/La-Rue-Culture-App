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
<%@page session="false" %>
<cq:include path="imagetext" resourceType="/apps/larue/components/paragraphs/imagetext" />
<cq:include path="par" resourceType="foundation/components/parsys" />
<div class="larue-related-articles">
    <div class="heading">RELATED ARTICLES</div>
    <cq:include path="relatedarticles" resourceType="/apps/larue/components/paragraphs/relatedarticles" />
</div>
<script>
jQuery(function () {
    $('.slick-simple-slideshow').slick({
        accessibility: false,
        arrows: false,
        dots: false
    });
});
</script>
