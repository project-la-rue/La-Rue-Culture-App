/*
*  Copyright 2016 Adobe Systems Incorporated. All rights reserved.
* 
*  This file is licensed to you under the Apache License, Version 2.0 (the "License"); 
*  you may not use this file except in compliance with the License. You may obtain a copy
*  of the License at http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under
*  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS 
*  OF ANY KIND, either express or implied. See the License for the specific language
*  governing permissions and limitations under the License.
*/

package com.adobe.larue.impl.servlets;

import org.apache.commons.io.IOUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@SlingServlet(
        label = "La Rue Proxy Servlet",
        description = "La Rue Proxy Servlet Implementation",
        paths = "/bin/larue/proxy",
        methods = {"GET", "POST"},
        metatype = true
)
public class ProxyServlet extends SlingAllMethodsServlet {
    final private static Logger log = LoggerFactory.getLogger(ProxyServlet.class);

    final private static String PARAM_ENDPOINT = "endpoint";

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        doRelay(req, resp);
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        doRelay(req, resp);
    }

    protected void doRelay(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        String endpoint = req.getParameter(PARAM_ENDPOINT);
        if (endpoint != null && endpoint.length() > 0) {
            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            Enumeration enumeration = req.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement().toString();
                connection.setRequestProperty(name, req.getHeader(name));
            }
            connection.setRequestMethod(req.getMethod());
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (HttpConstants.METHOD_POST.equals(req.getMethod())) {
                IOUtils.copy(req.getInputStream(), connection.getOutputStream());
            }
            resp.setStatus(connection.getResponseCode());
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null) {
                    for (String value : entry.getValue()) {
                        resp.setHeader(entry.getKey(), value);
                    }
                }
            }
            IOUtils.copy(connection.getInputStream(), resp.getOutputStream());
        }
    }
}
