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

package com.adobe.larue.dps2015.models;

import com.google.gson.JsonObject;

public class DpsCollection extends DpsEntity {
    private String id;
    private String layoutPath;
    private String contentElementsPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayoutPath() {
        return layoutPath;
    }

    public void setLayoutPath(String layoutPath) {
        this.layoutPath = layoutPath;
    }

    public String getContentElementsPath() {
        return contentElementsPath;
    }

    public void setContentElementsPath(String contentElementsPath) {
        this.contentElementsPath = contentElementsPath;
    }

    final private static String LAYOUT = "layout";
    final private static String CONTENT_ELEMENTS = "contentElements";

    @Override
    public void loadFromJson(JsonObject jsonObject) {
        super.loadFromJson(jsonObject);

        if (jsonObject.has(LINKS)) {
            JsonObject links = jsonObject.getAsJsonObject(LINKS);
            layoutPath = links.has(LAYOUT) ? links.getAsJsonObject(LAYOUT).get(HREF).getAsString() : null;
            contentElementsPath = links.has(CONTENT_ELEMENTS) ? links.getAsJsonObject(CONTENT_ELEMENTS).get(HREF).getAsString() : null;
        }
    }
}
