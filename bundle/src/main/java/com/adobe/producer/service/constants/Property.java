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

package com.adobe.producer.service.constants;

import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

public class Property {

	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String EXPIRES_IN = "expires_in";
	
	public static final String _LINKS = "_links";
	
	public static final String CONTENT_ELEMENTS = "contentElements";
	
	public static final String HREF = "href";
	
	public static final String CONTENT_URL = "contentUrl";
		
	public static final String API_KEY = "api_key";
	
	public static final String USER_TOKEN = "user_token";
	
	public static final String ENTITY_TYPE = "entityType";
	
	public static final String ENTITY_NAME = "entityName";
	
	public static final String ASPECT = "aspect";
	
	public static final String LAST_SUCCESS_DATE = "lastSuccessDate";
	
	public static final String TITLE = "title";
    
	public static final String SHORT_TITLE = "shortTitle";
	
    public static final String SHORT_ABSTRACT = "shortAbstract";

    public static final String KEYWORDS = "keywords";

    public static class AccessState {
        public static final String NAME = "accessState";
        public static final String FREE = "free";
        public static final String METERED = "metered";
        public static final String PROTECTED = "protected";
    }
    
    public static class Category {
        public static final String NAME = "category";
        public static final String RIDE = "ride";
    }
    
    public static class Keyword<T> {
        public final String name;
        public final T value;
        public Keyword(String name, T value) {
            this.name = name;
            this.value = value;
        }
        public boolean isValid() {
            return (!StringUtils.isEmpty(name) && value != null);
        }
    }
    
    public abstract static class KeywordValueParser<T> {
        public abstract T parse(String value);
    }
    
    public static class KeywordInfo<T> {
        public final Class<T> classInfo;
        public final KeywordValueParser<T> valueParser;
        public KeywordInfo(Class<T> classInfo, KeywordValueParser<T> valueParser) {
            this.classInfo = classInfo;
            this.valueParser = valueParser;
        }
    }
    
    public static class BaseKeywordParsers {
        
        protected final Map<String, KeywordInfo> keywordLookup = new HashMap<String, KeywordInfo>();
        
        public static final String IS_NEWS_FEED = "includeInNewsfeed";
        
        public static final String CATEGORY = "category";
        
        protected final KeywordValueParser<Long> longParser = new KeywordValueParser<Long>(){
            public Long parse(String value) {
                Long result = 0L;
                try {
                    result = Long.parseLong(value);
                } finally{

                }
                return result;
            }
        };
        
        protected final KeywordValueParser<Boolean> booleanParser = new KeywordValueParser<Boolean>(){
            public Boolean parse(String value) {
                Boolean result = false;
                try {
                    result = Boolean.parseBoolean(value);
                } finally{

                }
                return result;
            }
        };
        
        protected final KeywordValueParser<String> defaultParser = new KeywordValueParser<String>(){
            public String parse(String value) {
                return value;
            }
        };
        
        public BaseKeywordParsers() {
             keywordLookup.put(IS_NEWS_FEED, new KeywordInfo<Boolean>(Boolean.class, booleanParser));
             keywordLookup.put(CATEGORY, new KeywordInfo<String>(String.class, defaultParser));
        }
        
        public boolean shouldProcess(String keywordString) {
            String[] parts = keywordString.split(":");
            if (parts != null && parts.length > 1) {                
                return keywordLookup.containsKey(parts[0]);
            }
            return false;
        }
        
        public Keyword parse(String keywordString) {
            String[] parts = keywordString.split(":");
            if (parts == null || parts.length < 2) {
                return new Keyword<String>("Unkown", null);
            } else {
                String name = parts[0];
                Object value = null;
                KeywordValueParser parser = null;
                KeywordInfo info = keywordLookup.get(name);
                if (info != null) {
                    parser = info.valueParser;
                    if (parser != null && info.classInfo != null) {
                        try {
                            value = info.classInfo.cast(parser.parse(parts[1]));
                        } finally {}
                    }
                }
                return new Keyword(name, value);
            }
        }
    }
    
    public static class RideEventKeywordParsers extends BaseKeywordParsers {
            
        public static final String EVENT_START = "eventStart";

        public static final String EVENT_END = "eventEnd";

        public static final String EVENT_CITY = "eventCity";

        public static final String EVENT_COUNTRY = "eventCountry";

        public RideEventKeywordParsers() {
            keywordLookup.put(EVENT_START, new KeywordInfo<Long>(Long.class, longParser));
            keywordLookup.put(EVENT_END, new KeywordInfo<Long>(Long.class, longParser));
            keywordLookup.put(EVENT_CITY, new KeywordInfo<String>(String.class, defaultParser));
            keywordLookup.put(EVENT_COUNTRY, new KeywordInfo<String>(String.class, defaultParser));
        }
    }
}
