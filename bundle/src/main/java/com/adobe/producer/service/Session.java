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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.JSONObject;

import com.adobe.producer.service.constants.Parameter;
import com.adobe.producer.service.constants.Property;

public class Session {

	private static final String AUTHENTICATION_URL = "https://ims-na1.adobelogin.com/ims/token/v1";
	private static final String AUTHORIZATION_PREFIX = "bearer ";
	private static final String GRANT_TYPE = "device";
	private static final String SCOPE = "AdobeID,openid";
	
	private final String sessionId = UUID.randomUUID().toString();

	private String clientId;
	private String clientVersion;
	private String clientSecret;
	private String deviceId;
	private String deviceToken;
	private String accessToken;
	private long accessTokenExpiry;
	
	public Session(String clientId, String clientVersion, String clientSecret, String deviceId, String deviceToken) {
		this.clientId = clientId;
		this.clientVersion = clientVersion;
		this.clientSecret = clientSecret;
		this.deviceId = deviceId;
		this.deviceToken = deviceToken;
		this.accessToken = null;
		this.accessTokenExpiry = 0;
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientVersion() {
		return this.clientVersion;
	}
	
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceToken() {
		return this.deviceToken;
	}
	
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public long getAccessTokenExpiry() {
		return this.accessTokenExpiry;
	}
	
	public void setAccessTokenExpiry(long accessTokenExpiry) {
		this.accessTokenExpiry = accessTokenExpiry;
	}
	
	String doRequest(String url, Map<String, String> queryParameters, String method, Map<String, String> headers, String body) {
		try {
			if (accessToken == null || accessTokenExpiry < System.currentTimeMillis()) {
				updateAccessToken();
				return doRequest(url, queryParameters, method, headers, body);
			} else {
				HttpURLConnection connection = (HttpURLConnection) new URL(url + buildQueryString(queryParameters)).openConnection();
				connection.setRequestMethod(method);
				updateRequestHeaders(connection, headers);
				if ((method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) && body != null) {
					DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
					dataOutputStream.writeBytes(body);
					dataOutputStream.flush();
					dataOutputStream.close();
				}
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder responseStringBuilder = new StringBuilder();
				while ((inputLine = bufferedReader.readLine()) != null) {
					responseStringBuilder.append(inputLine);
				}
				bufferedReader.close();
				connection.disconnect();
				return responseStringBuilder.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void updateAccessToken() throws Exception {
		String url = AUTHENTICATION_URL
				+ "?" + Parameter.CLIENT_ID + "=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8.name())
				+ "&" + Parameter.CLIENT_SECRET + "=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8.name())
				+ "&" + Parameter.DEVICE_ID + "=" + URLEncoder.encode(deviceId, StandardCharsets.UTF_8.name())
				+ "&" + Parameter.DEVICE_TOKEN + "=" + URLEncoder.encode(deviceToken, StandardCharsets.UTF_8.name())
				+ "&" + Parameter.GRANT_TYPE + "=" + URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8.name())
				+ "&" + Parameter.SCOPE + "=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8.name());
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("POST");
		updateRequestHeaders(connection, null);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder responseStringBuilder = new StringBuilder();
		while ((inputLine = bufferedReader.readLine()) != null) {
			responseStringBuilder.append(inputLine);
		}
		bufferedReader.close();
		connection.disconnect();
		JSONObject responseObject = new JSONObject(responseStringBuilder.toString());
		accessToken = responseObject.getString(Property.ACCESS_TOKEN);
		accessTokenExpiry = System.currentTimeMillis() + responseObject.getLong(Property.EXPIRES_IN);
	}
	
	private String buildQueryString(Map<String, String> queryParameters) throws Exception {
		StringBuilder queryStringBuilder = new StringBuilder();
		if (queryParameters != null) {
			Iterator<Entry<String, String>> queryParameterIterator = queryParameters.entrySet().iterator();
			if (queryParameterIterator.hasNext()) {
				Entry<String, String> queryParameter = queryParameterIterator.next();
				queryStringBuilder.append("?" + URLEncoder.encode(queryParameter.getKey(), StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(queryParameter.getValue(), StandardCharsets.UTF_8.name()));
				while (queryParameterIterator.hasNext()) {
					queryParameter = queryParameterIterator.next();
					queryStringBuilder.append("&" + URLEncoder.encode(queryParameter.getKey(), StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(queryParameter.getValue(), StandardCharsets.UTF_8.name()));
				}
			}
		}
		return queryStringBuilder.toString();
	}
	
	private void updateRequestHeaders(HttpURLConnection connection, Map<String, String> headers) {
		if (headers != null) {
			for (Entry<String, String> header : headers.entrySet()) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		connection.setRequestProperty(Parameter.X_DPS_API_KEY, clientId);
		connection.setRequestProperty(Parameter.X_DPS_CLIENT_VERSION, clientVersion);
		connection.setRequestProperty(Parameter.X_DPS_CLIENT_SESSION_ID, sessionId);
		connection.setRequestProperty(Parameter.X_DPS_CLIENT_REQUEST_ID, UUID.randomUUID().toString());
		if (accessToken != null) {
			connection.setRequestProperty(Parameter.AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
		}
	}
}
