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

package com.adobe.producer.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adobe.producer.service.constants.EntityType;
import com.adobe.producer.service.constants.ProductionEndpoint;
import com.adobe.producer.service.constants.Property;

import com.adobe.larue.dps2015.models.DpsEntityListOptions;
    
import org.apache.commons.lang3.StringUtils;

public class ProducerService {

	private static final String PATH_PUBLICATION = "/publication";
	private static final String PATH_STATUS = "/status";
	
	public static String getPublication(Session session, String publicationId) {
		String url = ProductionEndpoint.PRODUCER_SERVICE + PATH_PUBLICATION + "/" + publicationId;
		return session.doRequest(url, null, "GET", null, null);
	}
	
	public static String getEntities(Session session, String publicationId, String entityType) {
        return getEntities(session, publicationId, entityType, null);
    }
    
	public static String getEntities(Session session, String publicationId, String entityType, DpsEntityListOptions options) {
		try {
			String url = ProductionEndpoint.PRODUCER_SERVICE + PATH_PUBLICATION + "/" + publicationId + "/" + entityType;
            if (options != null) {
                url += "/?" + options.toQueryString();
            }
			JSONArray entities = new JSONArray(session.doRequest(url, null, "GET", null, null));
			JSONArray result = new JSONArray();
			for (int index = 0; index < entities.length(); index++) {
				String href = entities.getJSONObject(index).getString(Property.HREF);
				result.put(new JSONObject(session.doRequest(ProductionEndpoint.PRODUCER_SERVICE + href, null, "GET", null, null)));
			}
			return result.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getEntity(Session session, String publicationId, String entityType, String entityName) {
		String url = ProductionEndpoint.PRODUCER_SERVICE + PATH_PUBLICATION + "/" + publicationId + "/" + entityType + "/" + entityName;
		return session.doRequest(url, null, "GET", null, null);
	}
	
	public static String getEntityContent(Session session, String publicationId, String entityType, String entityName) {
		try {
			String href = new JSONObject(getEntity(session, publicationId, entityType, entityName)).getJSONObject(Property._LINKS).getJSONObject(Property.CONTENT_URL).getString(Property.HREF);
			String url = ProductionEndpoint.PRODUCER_SERVICE + href;
			return session.doRequest(url, null, "GET", null, null);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCollectionContentElements(Session session, String publicationId, String entityName) {
		try {
            String collectionEntity = getEntity(session, publicationId, EntityType.COLLECTION, entityName);
            if (StringUtils.isEmpty(collectionEntity)) {
                return null;
            }
			String href = new JSONObject(collectionEntity).getJSONObject(Property._LINKS).getJSONObject(Property.CONTENT_ELEMENTS).getString(Property.HREF);
			String url = ProductionEndpoint.PRODUCER_SERVICE + href;
			JSONArray contents = new JSONArray(session.doRequest(url, null, "GET", null, null));
			JSONArray result = new JSONArray();
			for (int index = 0; index < contents.length(); index++) {
				href = contents.getJSONObject(index).getString(Property.HREF);
				result.put(new JSONObject(session.doRequest(ProductionEndpoint.PRODUCER_SERVICE + href, null, "GET", null, null)));
			}
			return result.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getEntityStatus(Session session, String publicationId, String entityType, String entityName) {
		String url = ProductionEndpoint.PRODUCER_SERVICE + PATH_STATUS + "/" + publicationId + "/" + entityType + "/" + entityName;
		return session.doRequest(url, null, "GET", null, null);
	}
}
