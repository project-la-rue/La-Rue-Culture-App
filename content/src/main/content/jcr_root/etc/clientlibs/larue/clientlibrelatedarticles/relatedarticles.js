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
function getRelatedArticles(url, options, onSuccess, onError) {
    var params = {};
    if (options != null) {
        $.extend(params, options);
    }
    $.ajax({
        method: "GET",
        url: url,
        data: params,
        dataType: "json"
    }).done(function (data) {
        if (data.hasOwnProperty("articles") && data.articles.length > 0) {
            if (onSuccess != null) {
                onSuccess(data);
            }
        } else {
            if (onError != null) onError("Articles missing from JSON response - ");
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        if (onError != null) onError(errorThrown);
    });
}