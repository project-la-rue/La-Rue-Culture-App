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
var LARUE = LARUE || {};
if (typeof LARUE.Profile === 'undefined') {
    LARUE.Profile = function () {
        this.CONST = {
            FIRSTNAME: 'larue.profile.firstname',
            LASTNAME: 'larue.profile.lastname',
            EMAIL: 'larue.profile.email',
            JOBTITLE: 'larue.profile.jobtitle',
            COUNTRY: 'larue.profile.country',
            COMPANY: 'larue.profile.company',
            INDUSTRY: 'larue.profile.industry',
            ADDRESS: 'larue.profile.address',
            CITY: 'larue.profile.city',
            STATE: 'larue.profile.state',
            ZIP: 'larue.profile.zip',
            PHONE: 'larue.profile.phone',
            EXP: 'larue.profile.exp',
            HEIGHT: 'larue.profile.height',
            PEDAL: 'larue.profile.pedal'
        };
        this.level = {
            11615: 'once a quarter',
            11616: 'once a month',
            11617: 'once a week',
            11618: '3+ times a week'
        };
        this.height = {
            11538: 'Small (5\' - 5\'7")',
            11539: 'Medium (5\'8" - 5\'11")',
            11540: 'Large (6\' - 6\'3")',
            11541: 'Xlarge (6\'4" +)'
        };
        this.pedal = {
            11550: 'Shimano Road',
            11551: 'Shimano MTB',
            11552: 'Look/KEO',
            11553: 'Time',
            11554: 'Speedplay ZERO',
            11555: 'Speedplay X',
            11556: 'Speedplay Light',
            11557: 'Flat (for sneakers)'
        };

        this.countryStates = {
          "10336": {
            "id": "united_states",
            "states": [
              {
                "code": 11648,
                "value": "Alabama"
              },
              {
                "code": 11649,
                "value": "Alaska"
              },
              {
                "code": 11650,
                "value": "American Samoa"
              },
              {
                "code": 11651,
                "value": "Arizona"
              },
              {
                "code": 11652,
                "value": "Arkansas"
              },
              {
                "code": 11653,
                "value": "Armed Forces America"
              },
              {
                "code": 11654,
                "value": "Armed Forces Europe"
              },
              {
                "code": 11655,
                "value": "Armed Forces Pacific"
              },
              {
                "code": 11656,
                "value": "California"
              },
              {
                "code": 11657,
                "value": "Colorado"
              },
              {
                "code": 11658,
                "value": "Connecticut"
              },
              {
                "code": 11659,
                "value": "Delaware"
              },
              {
                "code": 11660,
                "value": "District of Columbia"
              },
              {
                "code": 11661,
                "value": "Florida"
              },
              {
                "code": 11662,
                "value": "Georgia"
              },
              {
                "code": 11663,
                "value": "Hawaii"
              },
              {
                "code": 11664,
                "value": "Idaho"
              },
              {
                "code": 11665,
                "value": "Illinois"
              },
              {
                "code": 11666,
                "value": "Indiana"
              },
              {
                "code": 11667,
                "value": "Iowa"
              },
              {
                "code": 11668,
                "value": "Kansas"
              },
              {
                "code": 11669,
                "value": "Kentucky"
              },
              {
                "code": 11670,
                "value": "Louisiana"
              },
              {
                "code": 11671,
                "value": "Maine"
              },
              {
                "code": 11672,
                "value": "Mariana Islands"
              },
              {
                "code": 11673,
                "value": "Maryland"
              },
              {
                "code": 11674,
                "value": "Massachusetts"
              },
              {
                "code": 11675,
                "value": "Michigan"
              },
              {
                "code": 11676,
                "value": "Minnesota"
              },
              {
                "code": 11677,
                "value": "Mississippi"
              },
              {
                "code": 11678,
                "value": "Missouri"
              },
              {
                "code": 11679,
                "value": "Montana"
              },
              {
                "code": 11680,
                "value": "Nebraska"
              },
              {
                "code": 11681,
                "value": "Nevada"
              },
              {
                "code": 11682,
                "value": "New Hampshire"
              },
              {
                "code": 11683,
                "value": "New Jersey"
              },
              {
                "code": 11684,
                "value": "New Mexico"
              },
              {
                "code": 11685,
                "value": "New York"
              },
              {
                "code": 11686,
                "value": "North Carolina"
              },
              {
                "code": 11687,
                "value": "North Dakota"
              },
              {
                "code": 11688,
                "value": "Ohio"
              },
              {
                "code": 11689,
                "value": "Oklahoma"
              },
              {
                "code": 11690,
                "value": "Oregon"
              },
              {
                "code": 11691,
                "value": "Pacific Ocean Territories"
              },
              {
                "code": 11692,
                "value": "Palau"
              },
              {
                "code": 11693,
                "value": "Pennsylvania"
              },
              {
                "code": 11694,
                "value": "Rhode Island"
              },
              {
                "code": 11695,
                "value": "South Carolina"
              },
              {
                "code": 11696,
                "value": "South Dakota"
              },
              {
                "code": 11697,
                "value": "Tennessee"
              },
              {
                "code": 11698,
                "value": "Texas"
              },
              {
                "code": 11699,
                "value": "Utah"
              },
              {
                "code": 11700,
                "value": "Vermont"
              },
              {
                "code": 11701,
                "value": "Virginia"
              },
              {
                "code": 11702,
                "value": "Washington"
              },
              {
                "code": 11703,
                "value": "West Virginia"
              },
              {
                "code": 11704,
                "value": "Wisconsin"
              },
              {
                "code": 11705,
                "value": "Wyoming"
              }
            ]
          },
          "10337": {
            "id": "canada",
            "states": [
              {
                "code": 11716,
                "value": "Alberta"
              },
              {
                "code": 11717,
                "value": "British Columbia"
              },
              {
                "code": 11718,
                "value": "Manitoba"
              },
              {
                "code": 11719,
                "value": "New Brunswick"
              },
              {
                "code": 11720,
                "value": "Newfoundland"
              },
              {
                "code": 11721,
                "value": "North West Terr."
              },
              {
                "code": 11722,
                "value": "Nova Scotia"
              },
              {
                "code": 11723,
                "value": "Nunavut"
              },
              {
                "code": 11724,
                "value": "Ontario"
              },
              {
                "code": 11725,
                "value": "Prince Edward Island"
              },
              {
                "code": 11726,
                "value": "Quebec"
              },
              {
                "code": 11727,
                "value": "Saskatchewan"
              },
              {
                "code": 11728,
                "value": "Yukon"
              }
            ]
          },
          "10350": {
            "id": "australia",
            "states": [
              {
                "code": 11707,
                "value": "Australian Capital Territory"
              },
              {
                "code": 11708,
                "value": "New South Wales"
              },
              {
                "code": 11709,
                "value": "Northern Territory"
              },
              {
                "code": 11710,
                "value": "Queensland"
              },
              {
                "code": 11711,
                "value": "South Australia"
              },
              {
                "code": 11712,
                "value": "Tasmania"
              },
              {
                "code": 11713,
                "value": "Victoria"
              },
              {
                "code": 11714,
                "value": "Western Australia"
              }
            ]
          },
          "10381": {
            "id": "china",
            "states": [
              {
                "code": 11730,
                "value": "Anhui"
              },
              {
                "code": 11731,
                "value": "Beijing"
              },
              {
                "code": 11732,
                "value": "Chongqing"
              },
              {
                "code": 11733,
                "value": "Fujian"
              },
              {
                "code": 11734,
                "value": "Gansu"
              },
              {
                "code": 11735,
                "value": "Guangdong( include Shenzhen)"
              },
              {
                "code": 11736,
                "value": "Guangxi"
              },
              {
                "code": 11737,
                "value": "Guangzhou"
              },
              {
                "code": 11738,
                "value": "Guizhou"
              },
              {
                "code": 11739,
                "value": "Hainan"
              },
              {
                "code": 11740,
                "value": "Hebei"
              },
              {
                "code": 11741,
                "value": "Helongjiang"
              },
              {
                "code": 11742,
                "value": "Henan"
              },
              {
                "code": 11743,
                "value": "Hubei"
              },
              {
                "code": 11744,
                "value": "Hunan"
              },
              {
                "code": 11745,
                "value": "Inner Mongolia"
              },
              {
                "code": 11746,
                "value": "Jiangsu"
              },
              {
                "code": 11747,
                "value": "Jiangxi"
              },
              {
                "code": 11748,
                "value": "Jilin"
              },
              {
                "code": 11749,
                "value": "Liaoning"
              },
              {
                "code": 11750,
                "value": "Ningxia"
              },
              {
                "code": 11751,
                "value": "Qinghai"
              },
              {
                "code": 11752,
                "value": "Shaangxi"
              },
              {
                "code": 11753,
                "value": "Shandong"
              },
              {
                "code": 11754,
                "value": "Shanghai"
              },
              {
                "code": 11755,
                "value": "Shangxi"
              },
              {
                "code": 11756,
                "value": "Sichuan"
              },
              {
                "code": 11757,
                "value": "Tianjin"
              },
              {
                "code": 11758,
                "value": "Xinjiang"
              },
              {
                "code": 11759,
                "value": "Xizang (Tibet)"
              },
              {
                "code": 11760,
                "value": "Yunnan"
              },
              {
                "code": 11761,
                "value": "Zhejiang"
              }
            ]
          },
          "10434": {
            "id": "india",
            "states": [
              {
                "code": 11763,
                "value": "Andamans/Nicobar"
              },
              {
                "code": 11764,
                "value": "Andhra Pradesh"
              },
              {
                "code": 11765,
                "value": "Arunachal Pradesh"
              },
              {
                "code": 11766,
                "value": "Assam"
              },
              {
                "code": 11767,
                "value": "Bengal"
              },
              {
                "code": 11768,
                "value": "Bihar"
              },
              {
                "code": 11769,
                "value": "Chandigarh"
              },
              {
                "code": 11770,
                "value": "Chhattisgarh"
              },
              {
                "code": 11771,
                "value": "Dadra And Nagar Have"
              },
              {
                "code": 11772,
                "value": "Daman And Diu"
              },
              {
                "code": 11773,
                "value": "Goa"
              },
              {
                "code": 11774,
                "value": "Gujarat"
              },
              {
                "code": 11775,
                "value": "Haryana"
              },
              {
                "code": 11776,
                "value": "Himachal Pradesh"
              },
              {
                "code": 11777,
                "value": "Jammu and Kashmir"
              },
              {
                "code": 11778,
                "value": "Jharkhand"
              },
              {
                "code": 11779,
                "value": "Karnataka"
              },
              {
                "code": 11780,
                "value": "Kerala"
              },
              {
                "code": 11781,
                "value": "Ladakh"
              },
              {
                "code": 11782,
                "value": "Lakshadweep"
              },
              {
                "code": 11783,
                "value": "Madhya Pradesh"
              },
              {
                "code": 11784,
                "value": "Maharashtra"
              },
              {
                "code": 11785,
                "value": "Manipur"
              },
              {
                "code": 11786,
                "value": "Meghalaya"
              },
              {
                "code": 11787,
                "value": "Mizoram"
              },
              {
                "code": 11788,
                "value": "Nagaland"
              },
              {
                "code": 11789,
                "value": "New Delhi (or Delhi)"
              },
              {
                "code": 11790,
                "value": "Odisha"
              },
              {
                "code": 11791,
                "value": "Orrisa"
              },
              {
                "code": 11792,
                "value": "Pondicherry"
              },
              {
                "code": 11793,
                "value": "Puducherry"
              },
              {
                "code": 11794,
                "value": "Punjab"
              },
              {
                "code": 11795,
                "value": "Rajasthan"
              },
              {
                "code": 11796,
                "value": "Sikkim"
              },
              {
                "code": 11797,
                "value": "Tamil Nadu"
              },
              {
                "code": 11798,
                "value": "Tripura"
              },
              {
                "code": 11799,
                "value": "Uttar Pradesh"
              },
              {
                "code": 11800,
                "value": "Uttarakhand"
              },
              {
                "code": 11801,
                "value": "West Bengal"
              }
            ]
          },
          "10442": {
            "id": "japan",
            "states": [
              {
                "code": 11803,
                "value": "Akita-ken"
              },
              {
                "code": 11804,
                "value": "Aomori-ken"
              },
              {
                "code": 11805,
                "value": "Chiba-ken"
              },
              {
                "code": 11806,
                "value": "Ehime-ken"
              },
              {
                "code": 11807,
                "value": "Fukui-ken"
              },
              {
                "code": 11808,
                "value": "Fukuoka-ken"
              },
              {
                "code": 11809,
                "value": "Fukushima-ken"
              },
              {
                "code": 11810,
                "value": "Gifu-ken"
              },
              {
                "code": 11811,
                "value": "Gunma-ken"
              },
              {
                "code": 11812,
                "value": "Hiroshima-ken"
              },
              {
                "code": 11813,
                "value": "Hokkaido"
              },
              {
                "code": 11814,
                "value": "Hyogo-ken"
              },
              {
                "code": 11815,
                "value": "IBARAGI-KEN"
              },
              {
                "code": 11816,
                "value": "Ibaraki-ken"
              },
              {
                "code": 11817,
                "value": "Ishikawa-ken"
              },
              {
                "code": 11818,
                "value": "Iwate-ken"
              },
              {
                "code": 11819,
                "value": "Kagawa-ken"
              },
              {
                "code": 11820,
                "value": "Kagoshima-ken"
              },
              {
                "code": 11821,
                "value": "Kanagawa-ken"
              },
              {
                "code": 11822,
                "value": "Kochi-ken"
              },
              {
                "code": 11823,
                "value": "Kumamoto-ken"
              },
              {
                "code": 11824,
                "value": "Kyoto-fu"
              },
              {
                "code": 11825,
                "value": "Mie-ken"
              },
              {
                "code": 11826,
                "value": "Miyagi-ken"
              },
              {
                "code": 11827,
                "value": "Miyazaki-ken"
              },
              {
                "code": 11828,
                "value": "Nagano-ken"
              },
              {
                "code": 11829,
                "value": "Nagasaki-ken"
              },
              {
                "code": 11830,
                "value": "Nara-ken"
              },
              {
                "code": 11831,
                "value": "Niigata-ken"
              },
              {
                "code": 11832,
                "value": "OITA-KEN"
              },
              {
                "code": 11833,
                "value": "Okayama-ken"
              },
              {
                "code": 11834,
                "value": "Okinawa-ken"
              },
              {
                "code": 11835,
                "value": "Ooita-ken"
              },
              {
                "code": 11836,
                "value": "Osaka-fu"
              },
              {
                "code": 11837,
                "value": "Saga-ken"
              },
              {
                "code": 11838,
                "value": "Saitama-ken"
              },
              {
                "code": 11839,
                "value": "Shiga-ken"
              },
              {
                "code": 11840,
                "value": "Shimane-ken"
              },
              {
                "code": 11841,
                "value": "Shizuoka-ken"
              },
              {
                "code": 11842,
                "value": "Tochigi-ken"
              },
              {
                "code": 11843,
                "value": "Tokushima-ken"
              },
              {
                "code": 11844,
                "value": "Tokyo-to"
              },
              {
                "code": 11845,
                "value": "Tottori-ken"
              },
              {
                "code": 11846,
                "value": "Toyama-ken"
              },
              {
                "code": 11847,
                "value": "Wakayama-ken"
              },
              {
                "code": 11848,
                "value": "Yamagata-ken"
              },
              {
                "code": 11849,
                "value": "Yamaguchi-ken"
              },
              {
                "code": 11850,
                "value": "Yamanashi-ken"
              }
            ]
          }
        };

        this.restoreCountryStates = function() {
            var country = window.localStorage.getItem(this.CONST.COUNTRY);
            if (!country) {
				var codes = Object.keys(this.countryStates);
                if (codes instanceof Array && codes.length > 0) {
					country = codes[0];
                }
            }
            if (!!country) {
            	$('#country').val(country);
                this.constructCountryStates($("select#state"), country);
                var state = window.localStorage.getItem(this.CONST.STATE);
            	if (!!state) {
                	$('#state').val(state);
            	}
            }
        };

        this.constructCountryStates = function($select, country) {
            $select.empty();
            var countryStates = this.countryStates[country];
            if (!!countryStates && !!countryStates.states) {
                countryStates.states.forEach(function(s){
                    $select.append($("<option/>", {value: s.code, text: s.value}));
                });
				$("div#stateRow").slideDown(250);
            } else {
				$("div#stateRow").slideUp(250);
            }
        };

        this.constructDropdown = function ($select, options) {
            for (var val in options) {
                $option = $('<option value="' + val + '">' + options[val] + '</option>');
                $select.append($option);
            }
        };

        this.store = function () {
            window.localStorage.setItem(this.CONST.FIRSTNAME, $('#firstname').val());
            window.localStorage.setItem(this.CONST.LASTNAME, $('#lastname').val());
            window.localStorage.setItem(this.CONST.EMAIL, $('#email').val());
            window.localStorage.setItem(this.CONST.JOBTITLE, $('#jobtitle').val());
            window.localStorage.setItem(this.CONST.COUNTRY, $('#country').val());
            window.localStorage.setItem(this.CONST.COMPANY, $('#company').val());
            window.localStorage.setItem(this.CONST.INDUSTRY, $('#industry').val());
            window.localStorage.setItem(this.CONST.ADDRESS, $('#address').val());
            window.localStorage.setItem(this.CONST.CITY, $('#city').val());
			window.localStorage.setItem(this.CONST.STATE, $('#state').val());
			window.localStorage.setItem(this.CONST.ZIP, $('#zip').val());
            window.localStorage.setItem(this.CONST.PHONE, $('#phone').val());
            window.localStorage.setItem(this.CONST.EXP, $('#exp').val());
            window.localStorage.setItem(this.CONST.HEIGHT, $('#bikesize').val());
            window.localStorage.setItem(this.CONST.PEDAL, $('#pedal').val());
        };
        this.restore = function () {
            $('#firstname').val(window.localStorage.getItem(this.CONST.FIRSTNAME));
            $('#lastname').val(window.localStorage.getItem(this.CONST.LASTNAME));
            $('#email').val(window.localStorage.getItem(this.CONST.EMAIL));
            $('#jobtitle').val(window.localStorage.getItem(this.CONST.JOBTITLE));
            this.restoreCountryStates();
            $('#company').val(window.localStorage.getItem(this.CONST.COMPANY));
            $('#industry').val(window.localStorage.getItem(this.CONST.INDUSTRY));
            $('#address').val(window.localStorage.getItem(this.CONST.ADDRESS));
            $('#city').val(window.localStorage.getItem(this.CONST.CITY));
            $('#zip').val(window.localStorage.getItem(this.CONST.ZIP));
            $('#phone').val(window.localStorage.getItem(this.CONST.PHONE));
            $('#exp').val(window.localStorage.getItem(this.CONST.EXP));
            $('#bikesize').val(window.localStorage.getItem(this.CONST.HEIGHT));
            $('#pedal').val(window.localStorage.getItem(this.CONST.PEDAL));
        };
        this.setInfo = function () {
            $('.fullname').html((window.localStorage.getItem(this.CONST.FIRSTNAME) || '') + ' ' + (window.localStorage.getItem(this.CONST.LASTNAME) || ''));
            var jobtitle = window.localStorage.getItem(this.CONST.JOBTITLE) || '';
            var company = window.localStorage.getItem(this.CONST.COMPANY);
            if (company) {
                company = (jobtitle ? ' at ' : '') + company;
            } else {
                company = '';
            }
            $('.jobtitle').html(jobtitle + company);
        };

        var getOptionValue = function (value, options) {
            for (var val in options) {
                if (value === val) {
                    return options[value];
                }
            }
            return 'N/A';
        };

        this.setExp = function () {
            $('.level .value').html(getOptionValue(window.localStorage.getItem(this.CONST.EXP), this.level));
            $('.height .value').html(getOptionValue(window.localStorage.getItem(this.CONST.HEIGHT), this.height));
            $('.pedal .value').html(getOptionValue(window.localStorage.getItem(this.CONST.PEDAL), this.pedal));
        };
    };
}
