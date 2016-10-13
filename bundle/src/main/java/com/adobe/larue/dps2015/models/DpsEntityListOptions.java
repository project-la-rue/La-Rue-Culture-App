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

import java.util.ArrayList;
import java.util.List;

public class DpsEntityListOptions {
    public static final Integer DEFAULT_PAGE_SIZE = Integer.valueOf(25);
    public static final Integer DEFAULT_PAGE_NO = Integer.valueOf(0);

    public static enum FilterCombinator
    {
        ANY_OF(","),  ALL_OF(";");

        String value;

        private FilterCombinator(String value)
        {
            this.value = value;
        }
    }

    public static enum SortDirection
    {
        ASCENDING("x"),  DESCENDING("y");

        String value;

        private SortDirection(String value)
        {
            this.value = value;
        }
    }

    private Integer pageNo = DEFAULT_PAGE_NO;
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    private String sortByPropertyName = null;
    private SortDirection sortDirection = null;
    private List<DpsEntityType> entityTypes = new ArrayList<DpsEntityType>();
    private List<String> departments = new ArrayList<String>();
    private List<String> keywords = new ArrayList<String>();
    private FilterCombinator filterCombinator = FilterCombinator.ANY_OF;
    private Boolean freeContentOnly = null;

    public DpsEntityListOptions()
    {
    }

    public int getPageNo()
    {
        return this.pageNo.intValue();
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = Integer.valueOf(pageNo);
    }

    public int getPageSize()
    {
        return this.pageSize.intValue();
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = Integer.valueOf(pageSize);
    }

    public String getSortByPropertyName()
    {
        return this.sortByPropertyName;
    }

    public void setSortByPropertyName(String sortByPropertyName)
    {
        this.sortByPropertyName = sortByPropertyName;
    }

    public SortDirection getSortDirection()
    {
        return this.sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection)
    {
        this.sortDirection = sortDirection;
    }

    public void setFilterCombinator(FilterCombinator filterCombinator)
    {
        this.filterCombinator = filterCombinator;
    }

    public FilterCombinator getFilterCombinator()
    {
        return this.filterCombinator;
    }

    public List<DpsEntityType> getEntityTypes()
    {
        return this.entityTypes;
    }

    public void setDpsEntityTypes(List<DpsEntityType> entityTypes)
    {
        this.entityTypes = entityTypes;
    }

    public List<String> getDepartments()
    {
        return this.departments;
    }

    public void setDepartments(List<String> departments)
    {
        this.departments = departments;
    }

    public List<String> getKeywords()
    {
        return this.keywords;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }
    
    public Boolean getFreeContentOnly() {
        return this.freeContentOnly;
    }
    
    public void setFreeContentOnly(Boolean value) {
        this.freeContentOnly = value;
    }

    public String toQueryString() {
        String queryString = "";
        if (this.pageNo != null) {
            queryString = appendQueryParameter(queryString, "page", this.pageNo.toString());
        }
        if (this.pageSize != null) {
            queryString = appendQueryParameter(queryString, "pageSize", this.pageSize.toString());
        }
        if (this.sortByPropertyName != null) {
            queryString = appendQueryParameter(queryString, "sortField", this.sortByPropertyName.toString());
        }
        if (this.sortDirection != null) {
            queryString = appendQueryParameter(queryString, "descending", this.sortDirection == SortDirection.DESCENDING ? "true" : "false");
        }
        StringBuffer filterQueryString = null;
        for (DpsEntityType entityType : this.entityTypes) {
            filterQueryString = appendQueryExpression(filterQueryString, "entityType", entityType.toString());
        }
        for (String department : this.departments) {
            filterQueryString = appendQueryExpression(filterQueryString, "department", department);
        }
        for (String keyword : this.keywords) {
            filterQueryString = appendQueryExpression(filterQueryString, "keyword", keyword);
        }
        
        if (this.freeContentOnly != null && this.freeContentOnly) {
            filterQueryString = appendQueryExpression(filterQueryString, "accessState", "free");
        }
        
        if (filterQueryString != null) {
            queryString = appendQueryParameter(queryString, "q", filterQueryString.toString());
        }
        return queryString;
    }

    private StringBuffer appendQueryExpression(StringBuffer filterQueryString, String queryKey, String queryValue)
    {
        if (filterQueryString == null) {
            filterQueryString = new StringBuffer(queryKey).append("==").append(queryValue);
        } else {
            filterQueryString = filterQueryString.append(this.filterCombinator.value).append(queryKey).append("==").append(queryValue);
        }
        return filterQueryString;
    }

    private String appendQueryParameter(String queryString, String key, String value)
    {
        return queryString + (queryString.length() > 0 ? "&" : "") + key + "=" + value;
    }
}
