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
function renderRideSchedule(eventRegURL, rideScheduleURL, ridesBaseInformation, isLocal ){

    function getCSVFromFile(fileName, cb) {
            window.resolveLocalFileSystemURL(fileName, function (fileEntry) {
                fileEntry.file(function (file) {
                   Papa.parse(file, {complete: cb,header: true});
                }, errorHandler.bind(null, fileName));
            }, errorHandler.bind(null, fileName));
    }

    function getCSVFromServer(url, cb) {
        Papa.parse(url, {download: true, complete: cb, header: true});
    }

    var errorHandler = function (fileName, e) {  
        var msg = '';

        switch (e.code) {
            case FileError.QUOTA_EXCEEDED_ERR:
                msg = 'Storage quota exceeded';
                break;
            case FileError.NOT_FOUND_ERR:
                msg = 'File not found';
                break;
            case FileError.SECURITY_ERR:
                msg = 'Security error';
                break;
            case FileError.INVALID_MODIFICATION_ERR:
                msg = 'Invalid modification';
                break;
            case FileError.INVALID_STATE_ERR:
                msg = 'Invalid state';
                break;
            default:
                msg = 'Unknown error';
                break;
        };

        console.log('Error (' + fileName + '): ' + msg);
    }

    if (isLocal) {
    	getCSVFromFile( rideScheduleURL, onParseComplete );
    } else {
		getCSVFromServer( rideScheduleURL, onParseComplete);
    }
    
    function onParseComplete( results, file ) {
        var participants = results.data;
        
        var firstObject = results.data[0];
        
        //Create Ride Objects
        var rideObjects = JSON.parse(ridesBaseInformation);
        
        //Setup participants arrays in each ride Object
        for( var i = 0;i < rideObjects.length; i++ ) {
            rideObjects[i].participants = [];
        }
        
        //Construct the the ride event.
        
        var rideEvent = {
            name: firstObject["Survey Title"],
            rides: rideObjects
        };
        
        for( var i = 0; i< participants.length ; i++ ) {
            var participant = participants[i];
            for ( var j = 0; j < rideEvent.rides.length; j++ ) {
                if( participant[ rideEvent.rides[j].name ] ) {
                    rideEvent.rides[j].participants.push(participant);
                }
            }
        }
        
        // random connection avatars array
        var connectionsArray = [
            { 'name' : 'Brad Rencher',
              'url' : 'bradrencher.jpg'},
            { 'name' : 'Matt Asay',
              'url' : 'mattasay.jpg'},
            { 'name' : 'Ray Velez',
              'url' : 'rayvelez.jpg'},
            { 'name' : 'Rob Jonas',
              'url' : 'robjonas.jpg'},
            { 'name' : 'Ross Webster',
              'url' : 'rosswebster.jpg'},
            { 'name' : 'Sam Cannon',
              'url' : 'samcannon.jpg'},
            { 'name' : 'Sean Skammes',
              'url' : 'seanskammes.jpg'}    
        ];
        
        // generate random connections #
        
        for( var i = 0; i < rideObjects.length; i ++ ) {
            var numConnections = Math.floor((Math.random() * 5) + 3);
            rideObjects[i].connections = [];
        }
    
        var rides = rideEvent.rides;

        var rideScheduleContainer = document.getElementById('ride-schedule');
        
        var i = 0;
        while ( i < rides.length ) {

            var schedule = document.createElement('table');
            schedule.style.width = '100%';
            schedule.setAttribute('class', 'schedule-table');
            var scheduleContent = document.createElement('tbody');
            
            var currentRide = rides[i];
            var rideTime = getRideTimeObject( currentRide.rideTime );
            var rideDate = moment(currentRide.date);// new Date( currentRide.date );

            currentRide.startTime = moment(rideDate).add( rideTime.startTime.hour, 'hours').add( rideTime.startTime.minute, 'minutes');
            currentRide.endTime = moment(rideDate).add( rideTime.endTime.hour, 'hours').add(rideTime.endTime.minute, 'minutes');

            var dateRow = document.createElement('tr');
            var dateData = document.createElement('td');
            dateData.setAttribute('class','date-column');

            dateData.innerHTML = '<div class="date-number">' + rideDate.format('D') + '</div>' +
                '<div class="date-month">' + rideDate.format('MMM') + '</div>' +
                '<div class="date-day">' + rideDate.format('ddd') + '</div>';

            dateRow.appendChild(dateData);

            var rideData = document.createElement('td');
            rideData.setAttribute('class', 'ride-data');
            rideData.appendChild(createRideData( currentRide ));
            dateRow.appendChild(rideData);
            scheduleContent.appendChild(dateRow);

            var j = i + 1;
            var rowSpanCount = 1;

            while( j < rides.length ) {
                var nextRide = rides[j];
                if( nextRide.date == currentRide.date ) {
                    var rideRow = document.createElement('tr');
                    var rideData = document.createElement('td');
                    rideData.setAttribute('class', 'ride-data dashed-top ');
                    
                    rideTime = getRideTimeObject( nextRide.rideTime );
                   
                    nextRide.startTime = moment(rideDate).add( rideTime.startTime.hour, 'hours').add( rideTime.startTime.minute, 'minutes');
                    nextRide.endTime = moment(rideDate).add( rideTime.endTime.hour, 'hours').add( rideTime.endTime.minute, 'minutes');
                  
                    
                    rideData.appendChild( createRideData(nextRide));

                    rideRow.appendChild(rideData);
                    scheduleContent.appendChild(rideRow);
                    rowSpanCount++;
                    i++;
                }
                j++;
            }

            dateData.setAttribute('rowspan', rowSpanCount );
            schedule.appendChild(scheduleContent);
            rideScheduleContainer.appendChild(schedule);
            i++;
        }
    }
    
    function onParseError( error, file ) {
        console.log( error );
    }
    
    function getTimeofDay( dateMoment ) {
        if( dateMoment.get('hour') >= 12 ) {
            return 'PM';
        }
        else return 'AM';
    }
    
    function getHour( dateMoment ) {
         if( dateMoment.get('hour') > 12 ) {
            return dateMoment.get('hour') - 12;
        }
        else return dateMoment.get('hour');
    }
    
    function getRideTimeObject( timeString ) {
        timeString = timeString.replace(/ /g,'');
        var result = {};
        var splitTimes = timeString.split("-");

        var startTime = splitTimes[0].trim();
        var endTime = splitTimes[1].trim();

            var splitStart = startTime.split(":");
        var splitEnd = endTime.split(":");

        var startHour = parseInt(splitStart[0]);
        var startTOD = splitStart[1].substr(2);
        if( startTOD == "pm" && startHour != 12 ) {
            startHour = startHour + 12;
        }
        else if( startTOD == "am" && startHour == 12 ) {
                startHour = 0;
        }

        var endHour = parseInt(splitEnd[0]);
        var endTOD = splitEnd[1].substr(2);
        if( endTOD == "pm" && endHour != 12) {
            endHour = endHour + 12;
        }
        else if( endTOD == "am" && endHour == 12 ) {
                endHour = 0;
        }

        result.startTime = {
                hour: startHour,
          minute: parseInt(splitStart[1].substr(0,2))
        };

        result.endTime = {
            hour: endHour,
          minute: parseInt(splitEnd[1].substr(0,2))
        };

        return result;
    }

    function createRideData( ride )
    {        
        var rideContainer = document.createElement('div');        
        
        var rideTimeHTML = '<span class="time-hour">' + getHour( ride.startTime ) + '</span>';
        
        if( ride.startTime.get('minutes') > 0 ) rideTimeHTML += '<span class="time-minutes">.' + ride.startTime.get('minutes') + '</span>'; 
        
        rideTimeHTML += '<span class="time-am-pm"> ' + getTimeofDay( ride.startTime ) + ' - </span>' + 
                            '<span class="time-hour">' + getHour( ride.endTime ) + '</span>';
                            
        if( ride.endTime.get('minutes') > 0 ) rideTimeHTML += '<span class="time-minutes">.' + ride.endTime.get('minute') + '</span>';
        
        rideTimeHTML += '<span class="time-am-pm"> ' + getTimeofDay( ride.endTime ) + '</span>';
                                
        var rideTime = document.createElement('div');
        rideTime.setAttribute( 'class', 'ride-time');
        rideTime.innerHTML = rideTimeHTML;
        
        var rideInformation = document.createElement('div');
        rideInformation.setAttribute( 'class', 'ride-information');
        var rideInfoString = ( ride.connections && ( ride.connections.length > 0 ) ) ? 
                                    ride.participants.length + ' participants. ' + ride.connections.length + ' connections.' : 
                                    ride.participants.length + ' participants. ';
        
        rideInformation.appendChild(document.createTextNode( rideInfoString ) );
        
        var mapButton = document.createElement('div');
        mapButton.setAttribute( 'class', 'map-button');
        
        //this should be grabbed from the ride;
        mapButton.setAttribute( 'path-data', ride.path );
        mapButton.appendChild(document.createTextNode("MAP"));
        mapButton.addEventListener("click", openRideMap);
        
        var signupButton = document.createElement('div');
        signupButton.setAttribute( 'class', 'signup-button');
        signupButton.appendChild(document.createTextNode("BOOK"));
        signupButton.addEventListener("click", openRegpage);
        
        var remainingSpots = document.createElement('div');
        remainingSpots.setAttribute( 'class', 'remaining-spots');
        //remainingSpots.appendChild(document.createTextNode( '# left' ) );

		var base = "";
        var isLocal = (location.protocol.indexOf("file") == 0);
        var isIOS = /(iPhone|iPod|iPad)/i.test(navigator.userAgent);
        if (isLocal) {
            if (isIOS) {
				base = location.href.substring(0, location.href.lastIndexOf("article"));
            } else {
				base = "../";
            }
            base += "HTMLResources";
        }
        
        var connectionsAvatars = document.createElement('div');
        connectionsAvatars.setAttribute( 'class', 'connections-avatars');
        if( ride.connections && ride.connections.length > 0 ) {
          //create connections avatars         
            for( var i = 0; i< ride.connections.length; i++ ) {
                var avatar = document.createElement('div');
                avatar.setAttribute('class', 'avatar-img');
                avatar.setAttribute('style', 'background-image: url(' + base + '/content/dam/mobileapps/larue/cannes/avatars/' + ride.connections[i].url  +  ')');
                connectionsAvatars.appendChild( avatar );
            }
        }

        function noConnectivityAlert() {
			var alert = navigator.notification.alert || window.alert;
			alert("Your device is offline - please make sure it is connected to Internet and try again.");
        }

        function isOnline() {
			return (!isLocal||!navigator.connection)||(navigator.connection.type !== Connection.UNKNOWN && navigator.connection.type !== Connection.NONE);
        }

        function openRideMap(event){
            if (isOnline()) {
            	var url = base + "/content/mobileapps/larue/articles/fullscreenmappage.html";
            	var queryStr = "?pathdata=" + event.srcElement.getAttribute('path-data');
            	window.open(url+queryStr);
            } else {
				noConnectivityAlert();
            }
        }
        
        function openRegpage(event){
			if (isOnline()) {
            	var url = base + "/content/mobileapps/larue/articles/registrationPortal.html" + generateUserData();
            	window.open(url);
            } else {
				noConnectivityAlert();
            }
        }

        function getItem(k){return window.localStorage.getItem(k);};

        function generateUserData(){
            var profile = new LARUE.Profile();
			var queryStr = [profile.CONST.EMAIL,
                            profile.CONST.FIRSTNAME,
                            profile.CONST.LASTNAME,
                            profile.CONST.JOBTITLE,
                            profile.CONST.COMPANY,
                            profile.CONST.COUNTRY,
                            profile.CONST.ADDRESS,
                            profile.CONST.CITY,
                            profile.CONST.STATE,
                            profile.CONST.ZIP,
                            profile.CONST.PHONE,
                            profile.CONST.INDUSTRY,
                            profile.CONST.HEIGHT,
                            profile.CONST.PEDAL,
                            profile.CONST.EXP,
                            "own_bikeYN"].reduce(function(prev, currKey){
                    var currVal = getItem(currKey);
                    if (currVal == null) {
                        return prev;
                    }
                    var accumParam = (!!prev ? (prev+"&") : "?");
                    var currParam = currKey + "=" + encodeURIComponent(currVal);
                    return (accumParam + currParam);
            }, "");
            if (!!eventRegURL) {
                if (!!queryStr) {
                    queryStr += "&eventRegURL=" + eventRegURL;
                } else {
                    queryStr += "?eventRegURL=" + eventRegURL;
                }
            }
            return queryStr;
        }

        rideContainer.appendChild( rideTime );
        rideContainer.appendChild( rideInformation );
        if( ride.connections && ride.connections.length > 0 ) rideContainer.appendChild( connectionsAvatars );
        rideContainer.appendChild( mapButton );
        if (!!eventRegURL) {
        	rideContainer.appendChild( signupButton );
        }
        rideContainer.appendChild( remainingSpots );
        
        return rideContainer;
    }
}