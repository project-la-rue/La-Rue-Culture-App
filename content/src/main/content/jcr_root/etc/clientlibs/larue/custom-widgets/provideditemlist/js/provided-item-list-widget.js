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
(function ($) {

    function xmlStringToArray(xmlString) {
        var div = document.createElement("div");
        div.innerHTML = xmlString;
        return Array.prototype.slice.call(div.childNodes);
    }

    function stripHTMLTags(xmlString, delimiter) {
        var d = delimiter || "\n";
        return xmlStringToArray(xmlString).map(function (e) {
            return e.textContent;
        }).join(d);
    }

    $.widget("xfaWidget.providedItemListWidget", $.xfaWidget.textField, {
        _widgetName: "providedItemListWidget",
        render: function () {
            var $el = $.xfaWidget.textField.prototype.render.apply(this, arguments);
            var widgetRef = this;
            return $el;
        },
        getOptionsMap: function () {
            var parentOptionsMap = $.xfaWidget.textField.prototype.getOptionsMap.apply(this, arguments),
                newMap = $.extend({}, parentOptionsMap, {
                    displayValue: function () {
                        return this.$userControl.val();
                    }
                });
            return newMap;
        },
        getEventMap: function () {
            var parentEventMap = $.xfaWidget.textField.prototype.getEventMap.apply(this, arguments),
                newMap = $.extend({}, parentEventMap, {
                    blur: "xfaexit"
                });
            return newMap;
        },
        showDisplayValue: function () {
            return this.$userControl.text();
        },
        showValue: function () {
            //return this.$userControl.val();
            return stripHTMLTags(this.$userControl.val());
        },
        getCommitValue: function () {
            //return this.$userControl.val();
            var val = stripHTMLTags(this.$userControl.val());
            var lines = val.split("\n");
            return "<p>" + lines.reduce(function (prev, nxt) {
                if (nxt != null && nxt != "") {
                    prev += (nxt + "<br/>");
                };
                return prev;
            }, "") + "</p>";
        }
    });
})(jQuery);