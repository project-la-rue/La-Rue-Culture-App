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
function toggleYTVideoOfflineMsg() {
    if((navigator.network.connection.type).toUpperCase() != 'NONE' &&
       (navigator.network.connection.type).toUpperCase() != 'UNKNOWN') {
        $('.offline-overlay').hide();
    } else {
        $('.offline-overlay').show();
    }
}

document.addEventListener('deviceready', function() {
    document.addEventListener('online', toggleYTVideoOfflineMsg, false);
    document.addEventListener('offline', toggleYTVideoOfflineMsg, false);
    toggleYTVideoOfflineMsg();
}, false);