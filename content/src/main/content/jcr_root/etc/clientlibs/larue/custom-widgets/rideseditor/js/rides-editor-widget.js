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
(function($) {
    $.widget( "xfaWidget.ridesEditorWidget", $.xfaWidget.textField, {
        _widgetName:"ridesEditorWidget",
        render : function() {

            var $el = $.xfaWidget.textField.prototype.render.apply(this,arguments);

            var widgetRef = this;

			function commitRidesInfo(ridesInfoData) {
                var dataStr = JSON.stringify(ridesInfoData);
				widgetRef.$userControl.val(dataStr);
                widgetRef.$userControl.trigger("blur"); // need to blur and trigger a commit to xfa -
                return ridesInfoData;
            }

            function updateForDisplay(ridesInfoData) {
                var tableBody = $("table#ridesInfoList tbody");
                tableBody.empty();
                ridesInfoData.forEach(function(info){
                    var row = $("<tr/>", {class: "coral-Table-row"});
                    row.append($("<td/>", {class: "coral-Table-cell", text: info.name}));
                    row.append($("<td/>", {class: "coral-Table-cell", text: info.date}));
                    row.append($("<td/>", {class: "coral-Table-cell", text: info.rideTime}));
                    tableBody.append(row);
				});
                $("input#rideSourceName").val("");
                $("input#rideDate").val("");
                $("input#rideTime").val("");
            }

            $("#addRide").on("click", function(){
                var rideSourceName = $("input#rideSourceName").val();
                var rideDate = $("input#rideDate").val();
                var rideTime = $("input#rideTime").val();
                var selectorStr = "#routeListDropdown";
                var pathData = escape($(selectorStr).attr("data-path"));
                if (!rideSourceName || !rideDate || !rideTime || !pathData) {
                    alert("Required fields cannot be empty!");
                } else {
                    var newRideInfo = {name: rideSourceName,
                                       date: rideDate,
                                       rideTime: rideTime,
                                       path: pathData};
					var val = widgetRef.$userControl.val();
                	var ridesInfoData = val ? JSON.parse(val) : [];
                    ridesInfoData.push(newRideInfo);
                    commitRidesInfo(ridesInfoData);
					updateForDisplay(ridesInfoData);
                    if (ridesInfoData.length == 1) {
                        $("table#ridesInfoList thead").show();
                    }
                }
            });

 			$("#removeRide").on("click", function(){
				var val = widgetRef.$userControl.val();
                var ridesInfoData = val ? JSON.parse(val) : [];
                if (ridesInfoData.length > 0) {
					ridesInfoData.pop();
                }
                commitRidesInfo(ridesInfoData);
                updateForDisplay(ridesInfoData);
                if (ridesInfoData.length == 0) {
                    $("table#ridesInfoList thead").hide();
                }

            });
           return $el;
        },

        getOptionsMap: function(){
           var parentOptionsMap = $.xfaWidget.textField.prototype.getOptionsMap.apply(this,arguments),
           newMap = $.extend({},parentOptionsMap,
                               {
                                   "displayValue":function(value) {
                                       this.$userControl.val(value);
                                   }
                              });
           return newMap;
        },
        getEventMap: function() {
            var parentEventMap = $.xfaWidget.textField.prototype.getEventMap.apply(this,arguments),
            newMap = $.extend({},parentEventMap,
                               {
                                    blur: "xfaexit"
                              });
           return newMap;
        },
        showDisplayValue: function() {
           return this.$userControl.text();
        },
        showValue: function() {
            return this.$userControl.val();
        },
        getCommitValue: function() {
            return this.$userControl.val();
        }
    });
})(jQuery);