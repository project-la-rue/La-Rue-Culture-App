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

package com.adobe.larue.dps2015;

import com.adobe.larue.dps2015.models.*;
import com.adobe.larue.dps2015.utils.DateUtils;
import com.adobe.larue.dps2015.utils.SSLUtils;
import com.adobe.larue.dps2015.exceptions.DpsException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class DpsClient {
    private DpsConfiguration config;

    final private static String PROPERTY_ERROR = "error";

    public DpsClient(DpsConfiguration config) {
        this.config = config;
    }

    protected void refreshAccessToken() throws DpsException {
        if (config.getAccessTokenExpirationTime() == null || config.getAccessTokenExpirationTime().getTime() - 60000/* 1 minute */ < new Date().getTime()) {
            config.setClientSessionId(UUID.randomUUID().toString());

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Connection", "Close");
            headers.put("Accept", "application/json");

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=device");
            sb.append("&scope=AdobeID%2Copenid");
            sb.append("&client_id=");
            sb.append(config.getClientId());
            sb.append("&client_secret=");
            sb.append(config.getClientSecret());
            sb.append("&device_id=");
            sb.append(config.getDeviceId());
            sb.append("&device_token=");
            sb.append(config.getDeviceToken());

            String response = SSLUtils.post(DpsConstants.AUTHENTICATION_URL, headers, sb.toString());
            JsonElement jsonRoot = new JsonParser().parse(response);
            if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
                throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
            }
            JsonObject json = jsonRoot.getAsJsonObject();
            config.setAccessToken(json.get("access_token").getAsString());
            long time = new Date().getTime() + json.get("expires_in").getAsLong();
            config.setAccessTokenExpirationTime(new Date(time));
        }
    }

    public List<DpsProject> getProjects() throws DpsException {
        refreshAccessToken();
        Map<String, String> headers = generateHeaders();
        String response = SSLUtils.get(DpsConstants.AUTHORIZATION_URL, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonObject json = jsonRoot.getAsJsonObject();
        JsonArray publications = json.getAsJsonArray("masters").get(0).getAsJsonObject().getAsJsonArray("publications");
        List<DpsProject> publishers = new ArrayList<DpsProject>();
        for (JsonElement item : publications) {
            DpsProject project = new DpsProject();
            project.setId(item.getAsJsonObject().get("id").getAsString());
            project.setTitle(item.getAsJsonObject().get("title").getAsString());
            publishers.add(project);
        }
        return publishers;
    }

    public List<DpsProduct> getAllProducts(DpsProject project) throws DpsException {
        refreshAccessToken();
        List<DpsProduct> products = new ArrayList<DpsProduct>();

        Map<String, String> headers = generateHeaders();
        String url = DpsConstants.PRODUCT_URL + "/applications/" + project.getId() + "/products/";
        String response = SSLUtils.get(url, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonArray jsonProducts = jsonRoot.getAsJsonArray();
        for (JsonElement jsonProduct : jsonProducts) {
            DpsProduct product = new DpsProduct();
            product.loadFromJson(config, jsonProduct.getAsJsonObject());
            products.add(product);
        }
        return products;
    }

    public List<DpsEntity> getPagedEntities(DpsProject project, DpsEntityListOptions options) throws DpsException {
        refreshAccessToken();
        List<DpsEntity> entities = new ArrayList<DpsEntity>();
        Map<String, String> headers = generateHeaders();
        String url = DpsConstants.PRODUCER_URL + "/publication/" + project.getId() + "/entity?" + options.toQueryString();
        String response = SSLUtils.get(url, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonArray jsonEntities = jsonRoot.getAsJsonArray();
        for (JsonElement json : jsonEntities) {
            String href = json.getAsJsonObject().get("href").getAsString();
            entities.add(getEntity(href));
        }
        return entities;
    }

    public List<DpsEntity> getEntities(String entitiesPath) throws DpsException {
        refreshAccessToken();
        List<DpsEntity> entities = new ArrayList<DpsEntity>();
        Map<String, String> headers = generateHeaders();
        String url = DpsConstants.PRODUCER_URL + entitiesPath;
        String response = SSLUtils.get(url, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonArray jsonEntities = jsonRoot.getAsJsonArray();
        for (JsonElement json : jsonEntities) {
            String href = json.getAsJsonObject().get("href").getAsString();
            entities.add(getEntity(href));
        }
        return entities;
    }

    public DpsEntity getEntity(String entityPath) throws DpsException {
        refreshAccessToken();
        Map<String, String> headers = generateHeaders();
        String url = DpsConstants.PRODUCER_URL + entityPath;
        String response = SSLUtils.get(url, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonObject jsonObject = jsonRoot.getAsJsonObject();
        DpsEntityType entityType = DpsEntityType.fromValue(jsonObject.get("entityType").getAsString());
        DpsEntity entity = createEntity(entityType);
        entity.loadFromJson(jsonObject);
        return entity;
    }

    protected DpsEntity createEntity(DpsEntityType entityType) {
        switch (entityType) {
            case Collection:
                return new DpsCollection();
            case Article:
                return new DpsArticle();
            default:
                return null;
        }
    }

    public Date getPublishTime(DpsProject project, DpsEntity entity) throws DpsException {
        refreshAccessToken();
        Map<String, String> headers = generateHeaders();
        String url = DpsConstants.PRODUCER_URL + "/status/" + project.getId() + "/" + entity.getEntityType().toString() + "/" + entity.getEntityName();
        String response = SSLUtils.get(url, headers);
        JsonElement jsonRoot = new JsonParser().parse(response);
        if (jsonRoot.isJsonObject() && jsonRoot.getAsJsonObject().has(PROPERTY_ERROR)) {
            throw new DpsException(jsonRoot.getAsJsonObject().get(PROPERTY_ERROR).getAsString());
        }
        JsonArray states = jsonRoot.getAsJsonArray();
        for (JsonElement state : states) {
            JsonObject stateObject = state.getAsJsonObject();
            String aspect = stateObject.get("aspect").getAsString();
            String eventType = stateObject.get("eventType").getAsString();
            if ("publishing".equals(aspect) && "success".equals(eventType)) {
                return DateUtils.parseDate(stateObject.get("eventDate").getAsString());
            }
        }
        return null;
    }

    protected Map<String, String> generateHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "bearer " + config.getAccessToken());
        headers.put("Accept", "application/json");
        headers.put("X-DPS-Client-Versio", "test_version2");
        headers.put("X-DPS-Api-Key", config.getClientId());
        headers.put("X-DPS-Client-Request-Id", UUID.randomUUID().toString());
        headers.put("X-DPS-Client-Session-Id", config.getClientSessionId());
        return headers;
    }
}