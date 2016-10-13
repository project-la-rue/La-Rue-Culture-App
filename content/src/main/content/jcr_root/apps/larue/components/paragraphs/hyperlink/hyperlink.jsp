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
		import="com.day.cq.wcm.foundation.Image"%>
<%
Image image = new Image(resource);
image.setSelector(".img");
boolean hasImage = image.getChildren().iterator().hasNext() || properties.containsKey("fileReference");
String text = properties.get("text", "");
if (hasImage || !text.equals("")) {
	if (hasImage && !text.equals("")) {
		text = " " + text;
	}
} else {
	text = "Double click to add an icon or message to this button";
}

String url = properties.get("url", "");
String hrefAttr = "";
if (!url.equals("")) {
	hrefAttr = "href=\"" + url + "\"";
} else {
	text = "Double click to edit the hyperlink properties";
}

String style = properties.get("style", "");
String styleAttr = "";
if (style.equals("flat")) {
	styleAttr = "style=\"color:#" + properties.get("textColor", "666666") + ";background:none;font-weight:bold\"";
	if (!text.equals("")) {
		text += "<div class=\"arrow-right\" style=\"color:#" + properties.get("textColor", "666666") + "\"></div>";
	}
} else {
	styleAttr = "style=\"color:#" + properties.get("textColor", "666666") + ";background:#" + properties.get("backgroundColor", "CCCCCC") + ";font-weight:normal\"";
}
%>
<a class="larue-button" <%=styleAttr%> <%=hrefAttr%>><%if (hasImage) {image.draw(out);}%><%=text%></a>