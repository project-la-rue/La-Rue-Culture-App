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

jQuery(function () {
    if (LARUE && LARUE.Profile) {

        var profile = new LARUE.Profile();

        $('.edit').on('touchend', function () {
            profile.restore();
            $('.larue-profile-edit').show();
            $('.larue-myprofile').hide();
        });
        
        $('.save').on('touchend', function () {
            profile.store();
            profile.setInfo();
            profile.setExp();
            $('.larue-myprofile').show();
            $('.larue-profile-edit').hide();
        });
        
        $('select#country').on('change', function(evt){
            var country = $(evt.currentTarget).val();
            if (!!country) {
				profile.constructCountryStates($("select#state"), country);
            }
        });

        profile.setInfo();
        profile.setExp();
        
        profile.constructDropdown($('select#exp'),profile.level);
        profile.constructDropdown($('select#bikesize'),profile.height);
        profile.constructDropdown($('select#pedal'),profile.pedal);
    }
});