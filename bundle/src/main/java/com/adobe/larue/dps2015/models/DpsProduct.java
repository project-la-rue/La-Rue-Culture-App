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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DpsProduct {
    private String id;
    private String title;
    private String description;
    private Date availabilityDate;
    private DpsProductType type;
    private List<String> entityIds;

    public DpsProduct() {
        entityIds = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(Date availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public DpsProductType getType() {
        return type;
    }

    public void setType(DpsProductType type) {
        this.type = type;
    }

    final private static String ID = "id";
    final private static String LABEL = "label";
    final private static String IS_FREE = "isFree";
    final private static String IS_DISTRIBUTION_RESTRICTED = "isDistributionRestricted";
    final private static String AVAILABILITY_DATE = "availabilityDate";
    final private static String AVAILABILITY_DATE_TIMEZONE = "availabilityDateTimezone";
    final private static String CONTENT_IDS = "contentIds";

    public void loadFromJson(DpsConfiguration config, JsonObject jsonObject) {
        id = jsonObject.get(ID).getAsString();
        title = jsonObject.get(LABEL).getAsString();
        type = jsonObject.get(IS_FREE).getAsBoolean() ? DpsProductType.Free :
                (jsonObject.get(IS_DISTRIBUTION_RESTRICTED).getAsBoolean() ? DpsProductType.Restricted : DpsProductType.Entitled);
        if (jsonObject.has(AVAILABILITY_DATE)) {
            String timezone = jsonObject.has(AVAILABILITY_DATE_TIMEZONE) ? jsonObject.get(AVAILABILITY_DATE_TIMEZONE).getAsString() : null;
            availabilityDate = generateDate(jsonObject.get(AVAILABILITY_DATE).getAsLong(), timezone);
        }
        if (jsonObject.has(CONTENT_IDS)) {
            JsonArray ids = jsonObject.getAsJsonArray(CONTENT_IDS);
            for (JsonElement id : ids) {
                entityIds.add(id.getAsString());
            }
        }
    }

    protected Date generateDate(long datetime, String timezone) {
        return new Date(datetime);
    }
}
