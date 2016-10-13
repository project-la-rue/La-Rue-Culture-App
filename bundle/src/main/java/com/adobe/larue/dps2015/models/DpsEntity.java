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

import com.adobe.larue.dps2015.utils.DateUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DpsEntity {
    private String title;
    private String shortTitle;
    private String entityId;
    private String entityName;
    private DpsEntityType entityType;
    private String thumbnailUrl;
    private String socialShareUrl;
    private String contentPath;
    private boolean isPublishable;
    private Date modified;
    private Date published;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public DpsEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(DpsEntityType entityType) {
        this.entityType = entityType;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getSocialShareUrl() {
        return socialShareUrl;
    }

    public void setSocialShareUrl(String socialShareUrl) {
        this.socialShareUrl = socialShareUrl;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public boolean isPublishable() {
        return isPublishable;
    }

    public void setIsPublishable(boolean isPublishable) {
        this.isPublishable = isPublishable;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    final protected static String TITLE = "title";
    final protected static String SHORT_TITLE = "shortTitle";
    final protected static String ENTITY_ID = "entityId";
    final protected static String ENTITY_NAME = "entityName";
    final protected static String ENTITY_TYPE = "entityType";
    final protected static String MODIFIED = "modified";
    final protected static String LINKS = "_links";
    final protected static String SOCIAL_SHARE_URL = "socialShareUrl";
    final protected static String IS_PUBLISHABLE = "isPublishable";
    final protected static String CONTENT_URL = "contentUrl";
    final protected static String THUMBNAIL = "thumbnail";
    final protected static String HREF = "href";
    final protected static String CONTENTS_PREFIX = "contents/";
    final protected static String DOWN_SAMPLES = "downSamples";
    final protected static String SIZE = "size";


    public void loadFromJson(JsonObject jsonObject) {
        title = jsonObject.has(TITLE) ? jsonObject.get(TITLE).getAsString() : null;
        shortTitle = jsonObject.has(SHORT_TITLE) ? jsonObject.get(SHORT_TITLE).getAsString() : null;
        entityId = jsonObject.has(ENTITY_ID) ? jsonObject.get(ENTITY_ID).getAsString() : null;
        entityName = jsonObject.has(ENTITY_NAME) ? jsonObject.get(ENTITY_NAME).getAsString() : null;
        entityType = jsonObject.has(ENTITY_TYPE) ? DpsEntityType.fromValue(jsonObject.get(ENTITY_TYPE).getAsString()) : null;
        socialShareUrl = jsonObject.has(SOCIAL_SHARE_URL) ? jsonObject.get(SOCIAL_SHARE_URL).getAsString() : null;
        isPublishable = jsonObject.has(IS_PUBLISHABLE) ? jsonObject.get(IS_PUBLISHABLE).getAsBoolean() : false;
        if (jsonObject.has(MODIFIED)) {
            modified = DateUtils.parseDate(jsonObject.get(MODIFIED).getAsString());
        }
        if (jsonObject.has(LINKS)) {
            JsonObject links = jsonObject.getAsJsonObject(LINKS);
            contentPath = links.has(CONTENT_URL) ? links.getAsJsonObject(CONTENT_URL).get(HREF).getAsString() : null;
            if (links.has(THUMBNAIL)) {
                JsonObject thumbnail =  links.getAsJsonObject(THUMBNAIL);
                thumbnailUrl = thumbnail.has(HREF) ? thumbnail.get(HREF).getAsString() : null;
                if (thumbnail.has(DOWN_SAMPLES)) {
                    JsonArray samples = thumbnail.getAsJsonArray(DOWN_SAMPLES);
                    int minSize = Integer.MAX_VALUE;
                    for (JsonElement sample : samples) {
                        if (sample.isJsonObject()) {
                            JsonObject sampleObjest = sample.getAsJsonObject();
                            if (sampleObjest.has(SIZE) && sampleObjest.has(HREF)) {
                                int size = sampleObjest.get(SIZE).getAsInt();
                                if (minSize > size) {
                                    minSize = size;
                                    thumbnailUrl = sampleObjest.get(HREF).getAsString();
                                }
                            }
                        }
                    }
                }
            }
            if (thumbnailUrl != null && contentPath != null) {
                thumbnailUrl = thumbnailUrl.replace(CONTENTS_PREFIX, contentPath);
            }
            if (thumbnailUrl != null) {
                thumbnailUrl = DpsConstants.PRODUCER_URL + thumbnailUrl;
            }
        }
    }
}
