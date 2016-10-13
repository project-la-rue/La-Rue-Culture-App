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

import java.util.HashMap;
import java.util.Map;

public enum DpsEntityType {
    Article("article"), Collection("collection"), Banner("banner");

    private final String value;
    private static Map<String, DpsEntityType> constants;

    static {
        constants = new HashMap<String, DpsEntityType>();
        for (DpsEntityType c : values()) {
            constants.put(c.value, c);
        }
    }

    private DpsEntityType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static DpsEntityType fromValue(String value) {
        return value != null ? constants.get(value) : null;
    }
}
