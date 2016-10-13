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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.cm.ConfigurationAdmin;

import com.adobe.producer.service.ProducerService;
import com.adobe.producer.service.Session;
import com.adobe.producer.service.constants.EntityType;
import com.adobe.producer.service.constants.ProductionEndpoint;
import com.adobe.producer.service.constants.Property;
import com.adobe.larue.dps2015.models.DpsEntityListOptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.apache.commons.lang3.StringUtils;

@SlingServlet(label = "Adobe La Rue - Related Articles Servlet",
		paths = "/bin/larue/relatedArticles",
		methods = "GET",
		metatype = true)
@Service
public class RelatedArticlesServlet extends SlingSafeMethodsServlet {
	
    final private static Logger log = LoggerFactory.getLogger(RelatedArticlesServlet.class);
    
	private static final long serialVersionUID = 1L;
	
	private static final String CLIENT_VERSION = "1.0.0";
	private static final String CLIENT_ID = "clientId";
	private static final String CLIENT_SECRET = "clientSecret";
	private static final String COLLECTION = "collection";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	private static final String LAST_PUBLISHED = "lastPublished";
	private static final String PUBLISHING = "publishing";
	private static final String THUMBNAIL = "thumbnail";
	private static final String THUMBNAIL_PATH = "images/thumbnail";
	private static final String NAVTO = "navto";
	private static final String NAVTO_PROTOCOL = "navto://";
	private static final String ARTICLES = "articles";
	private static final String ARTICLE = "article";
	private static final String NUM_OF_RECORDS = "numOfRecords";
	private static final String NEWS_FEED_ONLY = "newsfeedOnly";
	private static final String RIDES_ONLY = "ridesOnly";
	private static final String FREE_ONLY = "freeOnly";
	
	private static final String DEFAULT_DEVICE_ID = "";
	private String deviceId = DEFAULT_DEVICE_ID;
	@org.apache.felix.scr.annotations.Property(label = "Device ID",
			description = "Configures the Device ID for the AEM Mobile project",
			value = DEFAULT_DEVICE_ID)
	public static final String PROPERTY_DEVICE_ID = "deviceId";
	
	private static final String DEFAULT_DEVICE_TOKEN = "";
	private String deviceToken = DEFAULT_DEVICE_TOKEN;
	@org.apache.felix.scr.annotations.Property(label = "Device Token",
			description = "Configures the Device Token for the AEM Mobile project",
			value = DEFAULT_DEVICE_TOKEN)
	public static final String PROPERTY_DEVICE_TOKEN = "deviceToken";
	
	private static final String DEFAULT_PUBLICATION_ID = "";
	private String publicationId = DEFAULT_PUBLICATION_ID;
	@org.apache.felix.scr.annotations.Property(label = "Publication ID",
			description = "Configures the Publication ID for the AEM Mobile project",
			value = DEFAULT_PUBLICATION_ID)
	public static final String PROPERTY_PUBLICATION_ID = "publicationId";
	
	@Reference
    private ConfigurationAdmin configurationAdmin;
	
    private static final Property.BaseKeywordParsers ridesParsers = new Property.RideEventKeywordParsers();
    private static final Property.BaseKeywordParsers baseParsers = new Property.BaseKeywordParsers();
    
	@Activate
	protected final void activate(final Map<String, String> properties) throws Exception {
		this.deviceId = PropertiesUtil.toString(properties.get(PROPERTY_DEVICE_ID), DEFAULT_DEVICE_ID);
		this.deviceToken = PropertiesUtil.toString(properties.get(PROPERTY_DEVICE_TOKEN), DEFAULT_DEVICE_TOKEN);
		this.publicationId = PropertiesUtil.toString(properties.get(PROPERTY_PUBLICATION_ID), DEFAULT_PUBLICATION_ID);
	}
	
    /**
    * This servlet endpoint is purposed to
    * 1. Gets a list of related articles given the request params "collection" and "article"
    * 2. Gets a list of Newsfeeds that is made up of articles that have the keyword "category:ride"
    **/
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		try {	
			org.osgi.service.cm.Configuration adobeDPSClientConfiguration = configurationAdmin.getConfiguration("com.adobe.cq.mobile.dps.impl.service.AdobeDPSClient");
	        Dictionary<String, Object> properties = adobeDPSClientConfiguration.getProperties();
	        String clientId = properties.get(CLIENT_ID).toString();
	        String clientSecret = properties.get(CLIENT_SECRET).toString();
	        String entityName = request.getParameter(COLLECTION);
	        String articleName = request.getParameter(ARTICLE);

			Session session = new Session(clientId, CLIENT_VERSION, clientSecret, deviceId, deviceToken);
			ArrayList<JSONObject> articles = new ArrayList<JSONObject>();
            String strCollectionElements = null;
            String strNewsfeedOnly = request.getParameter(NEWS_FEED_ONLY);
            Boolean newsfeedOnly = StringUtils.isEmpty(strNewsfeedOnly)?false:Boolean.parseBoolean(strNewsfeedOnly);
            String strRidesOnly = request.getParameter(RIDES_ONLY);
            Boolean ridesOnly = StringUtils.isEmpty(strRidesOnly)?false:Boolean.parseBoolean(strRidesOnly);
            String strFreeOnly = request.getParameter(FREE_ONLY);
            Boolean freeOnly = StringUtils.isEmpty(strFreeOnly)?false:Boolean.parseBoolean(strFreeOnly);
            
            /**
            * Gets a list of articles based on the request parameters:
            * newsfeedOnly: gets articles that have the keyword "includeInNewsfeed:true"
            * rideOnly: gets articles that have the keyword "category:true"
            * freeOnly: gets articles that have accessState set to Free
            **/
            if (StringUtils.isEmpty(entityName)) {   
                if (newsfeedOnly||ridesOnly||freeOnly) {
                    DpsEntityListOptions filters = new DpsEntityListOptions();
                    filters.setFilterCombinator(DpsEntityListOptions.FilterCombinator.ALL_OF);
                    List<String> keywordList = new ArrayList<String>();
                    filters.setKeywords(keywordList);
                    if (newsfeedOnly) {
                        keywordList.add(Property.RideEventKeywordParsers.IS_NEWS_FEED+":true");
                    }
                    if (ridesOnly) {
                        keywordList.add(Property.BaseKeywordParsers.CATEGORY+":"+Property.Category.RIDE);
                    }
                    if (freeOnly) {
                        filters.setFreeContentOnly(true);
                    }
                    log.debug("La Rue - Related articles - " + filters.toQueryString());
                    strCollectionElements = ProducerService.getEntities(session, publicationId, ARTICLE, filters);
                } else {
                    strCollectionElements = ProducerService.getEntities(session, publicationId, ARTICLE);
                }
            } else {
                //if collection (entityName) is given, get all articles under it.
                strCollectionElements = ProducerService.getCollectionContentElements(session, publicationId, entityName);
            }
            
            System.out.println(strCollectionElements);
            if (!StringUtils.isEmpty(strCollectionElements)) { 
                JSONArray collectionContentElements = new JSONArray(strCollectionElements);
                for (int i = 0; i < collectionContentElements.length(); i++) {
                    JSONObject entity = collectionContentElements.getJSONObject(i);
                    if (entity.has(Property.ENTITY_TYPE) && entity.getString(Property.ENTITY_TYPE).equals(EntityType.ARTICLE)) {
                        if (entity.has(Property.ENTITY_NAME) && !entity.getString(Property.ENTITY_NAME).equals(articleName)) {
                            JSONArray entityStatus = new JSONArray(ProducerService.getEntityStatus(session, publicationId, EntityType.ARTICLE, entity.getString(Property.ENTITY_NAME)));
                            for (int j = 0; j < entityStatus.length(); j++) {
                                JSONObject status = entityStatus.getJSONObject(j);
                                if (status.has(Property.ASPECT) && status.getString(Property.ASPECT).equals(PUBLISHING)) {
                                    if (!status.isNull(Property.LAST_SUCCESS_DATE)) {
                                        entity.put(LAST_PUBLISHED, status.getString(Property.LAST_SUCCESS_DATE));
                                        articles.add(entity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
            * The rest of the logic is to build out a result JSON that contains a list of articles each with metadata
            **/
			JSONObject result = new JSONObject();
			result.put(ARTICLES, new JSONArray());
            
            String strNumOfRecords = request.getParameter(NUM_OF_RECORDS);
            Integer numOfRecords = null;
            if (!StringUtils.isEmpty(strNumOfRecords)) {
               numOfRecords = Integer.parseInt(strNumOfRecords);
            }
            numOfRecords = (numOfRecords==null?articles.size():Math.min(articles.size(), numOfRecords));
			
            for (int i = 0; i < numOfRecords; i++) {
				JSONObject article = articles.get(i);
				String thumbnail = ProductionEndpoint.PRODUCER_SERVICE
						+ article.getJSONObject(Property._LINKS).getJSONObject(Property.CONTENT_URL).getString(Property.HREF)
						+ THUMBNAIL_PATH
						+ "?" + Property.API_KEY + "=" + session.getClientId()
						+ "&" + Property.USER_TOKEN + "=" + session.getAccessToken();
				
                JSONObject resultArticleJson = new JSONObject()
                    .put(Property.SHORT_TITLE, article.optString(Property.SHORT_TITLE, ""))
                    .put(Property.SHORT_ABSTRACT, article.optString(Property.SHORT_ABSTRACT, ""))
                    .put(THUMBNAIL, thumbnail)
                    .put(Property.TITLE, article.getString(Property.TITLE))
                    .put(NAVTO, NAVTO_PROTOCOL + article.getString(Property.ENTITY_NAME));
                
                
                JSONArray unprocessedKeywords = new JSONArray();
                JSONArray keywords = article.optJSONArray(Property.KEYWORDS);
                boolean isRideArticle = keywords == null ? false : (keywords.toString().indexOf(Property.Category.NAME+":"+Property.Category.RIDE)>-1);
                Property.BaseKeywordParsers parsers = isRideArticle ? ridesParsers : baseParsers;
                int keywordLength = keywords == null?0:keywords.length();
                String keyword = null;
                if (keywordLength > 0) {
                    for (int j = 0; j < keywordLength; j++) {
                        keyword = keywords.getString(j);
                        if (parsers.shouldProcess(keyword)) {
                            Property.Keyword k = parsers.parse(keyword);
                            if (k.isValid()) resultArticleJson.put(k.name, k.value);
                        } else {
                            unprocessedKeywords.put(keyword);
                        }
                    }
                    if (unprocessedKeywords.length()>0) {
                        resultArticleJson.put(Property.KEYWORDS, unprocessedKeywords);
                    }
                }
				result.getJSONArray(ARTICLES).put(i, resultArticleJson);
			}
            
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(result.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
