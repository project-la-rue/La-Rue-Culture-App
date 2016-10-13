<%------------------------------------------------------------------------
 ~
 ~ Copyright 2016 Adobe Systems Incorporated. All rights reserved.
 ~ 
 ~ This file is licensed to you under the Apache License, Version 2.0 (the "License"); 
 ~ you may not use this file except in compliance with the License. You may obtain a copy
 ~ of the License at http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software distributed under
 ~ the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS 
 ~ OF ANY KIND, either express or implied. See the License for the specific language
 ~ governing permissions and limitations under the License.
 ~
 --------------------------------------------------------------------------%><%
%><%@include file="/libs/foundation/global.jsp"%>
    <%@page session="false" import="com.day.cq.rewriter.linkchecker.LinkCheckerSettings"%>
        <% LinkCheckerSettings.fromRequest(slingRequest).setIgnoreExternals(true); %>
            <div class="larue-myprofile">
                <div class="page-header">
                    <div class="title">
                        <%= pageProperties.get("dps-title","") %>
                    </div>
                    <div class="abstract edit">
                        Edit
                    </div>
                </div>
                <div class="hero">
                    <div class="info">
                        <div class="fullname"></div>
                        <div class="jobtitle"></div>
                    </div>
                </div>
                <div class="properties">
                    <div class="box level">
                        <div class="title">LEVEL</div>
                        <div class="value"></div>
                    </div>
                    <div class="box height">
                        <div class="title">HEIGHT</div>
                        <div class="value"></div>
                    </div>
                    <div class="box pedal">
                        <div class="title">PEDAL</div>
                        <div class="value"></div>
                    </div>
                </div>
            </div>
            <div class="larue-profile-edit">
                <div class="hero">
                    <img class="avatar-bg" src="/etc/designs/larue/icons/avatar-placeholder.png" />
                    <img class="avatar" src="/etc/designs/larue/icons/avatar-placeholder.png" />
                    <div class="fullname"></div>
                    <div class="jobtitle"></div>
                </div>
                <div class="signup-form">
                    <div class="signup-heading">CONTACT INFO</div>
                    <div class="signup-subheading">We use this to notify you of special ride updates or changes</div>
                    <div class="signup-form-field"><span class="label">EMAIL</span>
                        <input class="value" type="text" id="email" />
                    </div>
                    <div class="signup-form-field"><span class="label">FIRST NAME</span>
                        <input class="value" type="text" id="firstname" />
                    </div>
                    <div class="signup-form-field"><span class="label">LAST NAME</span>
                        <input class="value" type="text" id="lastname" />
                    </div>
                    <div class="signup-form-field"><span class="label">JOB TITLE</span>
                        <input class="value" type="text" id="jobtitle" />
                    </div>
                    <div class="signup-form-field"><span class="label">COMPANY</span>
                        <input class="value" type="text" id="company" />
                    </div>
                    <div class="signup-form-field"><span class="label">INDUSTRY</span>
                        <select class="value" id="industry">
                            <option value="">-- Please Select --</option>
                            <option value="10049" title="Advertising">Advertising</option>
                            <option value="10050" title="Agriculture, Fishing and Forestry">Agriculture, Fishing and Forestry</option>
                            <option value="10051" title="Architecture">Architecture</option>
                            <option value="10052" title="Broadcast/Cable/TV/Radio">Broadcast/Cable/TV/Radio</option>
                            <option value="10053" title="Construction">Construction</option>
                            <option value="10054" title="Distribution/Wholesale">Distribution/Wholesale</option>
                            <option value="11460" title="Education: Higher ed">Education: Higher ed</option>
                            <option value="11461" title="Education: K-12">Education: K-12</option>
                            <option value="11462" title="Engineering/Management services">Engineering/Management services</option>
                            <option value="11463" title="Film/Video/Animation Production">Film/Video/Animation Production</option>
                            <option value="10058" title="Financial Services">Financial Services</option>
                            <option value="11464" title="Government: Federal">Government: Federal</option>
                            <option value="11465" title="Government: Military/Aerospace/Defense">Government: Military/Aerospace/Defense</option>
                            <option value="11466" title="Government: State/Local">Government: State/Local</option>
                            <option value="10062" title="Graphic Design">Graphic Design</option>
                            <option value="10063" title="Healthcare">Healthcare</option>
                            <option value="11467" title="Hospitality/Food services">Hospitality/Food services</option>
                            <option value="10066" title="Insurance">Insurance</option>
                            <option value="10067" title="Legal">Legal</option>
                            <option value="10068" title="Manufacturing">Manufacturing</option>
                            <option value="10676" title="Media/Entertainment/Arts">Media/Entertainment/Arts</option>
                            <option value="10070" title="Membership Organization">Membership Organization</option>
                            <option value="10071" title="Mining">Mining</option>
                            <option value="10072" title="Non-Profit/Charity">Non-Profit/Charity</option>
                            <option value="10073" title="Online Commerce">Online Commerce</option>
                            <option value="11468" title="Other">Other</option>
                            <option value="10075" title="Pharmaceuticals/Biotech">Pharmaceuticals/Biotech</option>
                            <option value="10076" title="Photography">Photography</option>
                            <option value="11469" title="Printing/Publishing">Printing/Publishing</option>
                            <option value="11470" title="Professional Services (Agency/Business)">Professional Services (Agency/Business)</option>
                            <option value="11471" title="Professional Services (Technical,Web,IT)">Professional Services (Technical,Web,IT)</option>
                            <option value="10080" title="Public Relations">Public Relations</option>
                            <option value="10081" title="Real Estate">Real Estate</option>
                            <option value="10082" title="Retail">Retail</option>
                            <option value="10083" title="Services">Services</option>
                            <option value="10084" title="Technology/High Tech">Technology/High Tech</option>
                            <option value="10677" title="Telecommunications">Telecommunications</option>
                            <option value="10086" title="Transportation">Transportation</option>
                            <option value="10087" title="Utilities">Utilities</option>
                        </select>
                    </div>
                    <div class="signup-form-field"><span class="label">ADDRESS</span>
                        <input class="value" type="text" id="address" />
                    </div>
                    <div class="signup-form-field"><span class="label">CITY</span>
                        <input class="value" type="text" id="city" />
                    </div>
                    <div class="signup-form-field"><span class="label">ZIP</span>
                        <input class="value" type="text" id="zip"/>
                    </div>
                    <div class="signup-form-field"><span class="label">COUNTRY</span>
                        <select id="country" class="value">
                            <option value="">-- Please Select --</option>
                            <option value="10336" title="United States">United States</option>
                            <option value="10337" title="Canada">Canada</option>
                            <option value="10434" title="India">India</option>
                            <option value="10350" title="Australia">Australia</option>
                            <option value="10490" title="New Zealand">New Zealand</option>
                            <option value="10338" title="Afghanistan">Afghanistan</option>
                            <option value="10339" title="Albania">Albania</option>
                            <option value="10340" title="Algeria">Algeria</option>
                            <option value="10341" title="American Samoa">American Samoa</option>
                            <option value="10342" title="Andorra">Andorra</option>
                            <option value="10343" title="Angola">Angola</option>
                            <option value="10344" title="Anguilla">Anguilla</option>
                            <option value="10345" title="Antarctica">Antarctica</option>
                            <option value="10346" title="Antigua/Barbuda">Antigua/Barbuda</option>
                            <option value="10347" title="Argentina">Argentina</option>
                            <option value="10348" title="Armenia">Armenia</option>
                            <option value="10349" title="Aruba">Aruba</option>
                            <option value="10351" title="Austria">Austria</option>
                            <option value="10352" title="Azerbaijan">Azerbaijan</option>
                            <option value="10353" title="Bahamas">Bahamas</option>
                            <option value="10354" title="Bahrain">Bahrain</option>
                            <option value="10355" title="Bangladesh">Bangladesh</option>
                            <option value="10356" title="Barbados">Barbados</option>
                            <option value="10357" title="Belarus">Belarus</option>
                            <option value="10358" title="Belgium">Belgium</option>
                            <option value="10359" title="Belize">Belize</option>
                            <option value="10360" title="Benin">Benin</option>
                            <option value="10361" title="Bermuda">Bermuda</option>
                            <option value="10362" title="Bhutan">Bhutan</option>
                            <option value="10363" title="Bolivia">Bolivia</option>
                            <option value="10364" title="Bosnia-Herzeg.">Bosnia-Herzeg.</option>
                            <option value="10365" title="Botswana">Botswana</option>
                            <option value="10366" title="Bouvet Island">Bouvet Island</option>
                            <option value="10367" title="Brazil">Brazil</option>
                            <option value="10368" title="Brit Ind Ocn Tr">Brit Ind Ocn Tr</option>
                            <option value="10369" title="Brit.Virgin Is.">Brit.Virgin Is.</option>
                            <option value="10370" title="Brunei">Brunei</option>
                            <option value="10371" title="Bulgaria">Bulgaria</option>
                            <option value="10372" title="Burkina Faso">Burkina Faso</option>
                            <option value="10373" title="Burundi">Burundi</option>
                            <option value="10374" title="Cambodia">Cambodia</option>
                            <option value="10375" title="Cameroon">Cameroon</option>
                            <option value="10376" title="Cape Verde Is.">Cape Verde Is.</option>
                            <option value="10377" title="Cayman Islands">Cayman Islands</option>
                            <option value="10378" title="Central Afr.Rep">Central Afr.Rep</option>
                            <option value="10379" title="Chad">Chad</option>
                            <option value="10380" title="Chile">Chile</option>
                            <option value="10381" title="China">China</option>
                            <option value="10382" title="Christmas Islnd">Christmas Islnd</option>
                            <option value="10383" title="Cocos Islands">Cocos Islands</option>
                            <option value="10384" title="Colombia">Colombia</option>
                            <option value="10385" title="Comoros">Comoros</option>
                            <option value="10386" title="Congo">Congo</option>
                            <option value="10387" title="Cook Islands">Cook Islands</option>
                            <option value="10388" title="Costa Rica">Costa Rica</option>
                            <option value="10389" title="Cote D'Ivoire">Cote D'Ivoire</option>
                            <option value="10390" title="Croatia">Croatia</option>
                            <option value="10391" title="Cyprus">Cyprus</option>
                            <option value="10392" title="Czech Republic">Czech Republic</option>
                            <option value="10393" title="Denmark">Denmark</option>
                            <option value="10394" title="Djibouti">Djibouti</option>
                            <option value="10395" title="Dominica">Dominica</option>
                            <option value="10396" title="Dominican Rep.">Dominican Rep.</option>
                            <option value="10397" title="Ecuador">Ecuador</option>
                            <option value="10398" title="Egypt">Egypt</option>
                            <option value="10399" title="El Salvador">El Salvador</option>
                            <option value="10400" title="Equatorial Gui.">Equatorial Gui.</option>
                            <option value="10401" title="Eritrea">Eritrea</option>
                            <option value="10402" title="Estonia">Estonia</option>
                            <option value="10403" title="Ethiopia">Ethiopia</option>
                            <option value="10404" title="Falkland Islnds">Falkland Islnds</option>
                            <option value="10405" title="Faroe Islands">Faroe Islands</option>
                            <option value="10406" title="Fiji">Fiji</option>
                            <option value="10407" title="Finland">Finland</option>
                            <option value="10408" title="France">France</option>
                            <option value="10409" title="Fren.Polynesia">Fren.Polynesia</option>
                            <option value="10410" title="French Guyana">French Guyana</option>
                            <option value="10411" title="French Sthrn Tr">French Sthrn Tr</option>
                            <option value="10412" title="Gabon">Gabon</option>
                            <option value="10413" title="Gambia">Gambia</option>
                            <option value="10414" title="Georgia">Georgia</option>
                            <option value="10415" title="Germany">Germany</option>
                            <option value="10416" title="Ghana">Ghana</option>
                            <option value="10417" title="Gibraltar">Gibraltar</option>
                            <option value="10418" title="Greece">Greece</option>
                            <option value="10419" title="Greenland">Greenland</option>
                            <option value="10420" title="Grenada">Grenada</option>
                            <option value="10421" title="Guadeloupe">Guadeloupe</option>
                            <option value="10422" title="Guam">Guam</option>
                            <option value="10423" title="Guatemala">Guatemala</option>
                            <option value="10424" title="Guernsey">Guernsey</option>
                            <option value="10425" title="Guinea">Guinea</option>
                            <option value="10426" title="Guinea-Bissau">Guinea-Bissau</option>
                            <option value="10427" title="Guyana">Guyana</option>
                            <option value="10428" title="Haiti">Haiti</option>
                            <option value="10655" title="Heard&amp;McDom Isl">Heard&amp;McDom Isl</option>
                            <option value="10430" title="Honduras">Honduras</option>
                            <option value="10431" title="Hong Kong">Hong Kong</option>
                            <option value="10432" title="Hungary">Hungary</option>
                            <option value="10433" title="Iceland">Iceland</option>
                            <option value="10435" title="Indonesia">Indonesia</option>
                            <option value="10436" title="Iraq">Iraq</option>
                            <option value="10437" title="Ireland">Ireland</option>
                            <option value="10438" title="Isle of Man">Isle of Man</option>
                            <option value="10439" title="Israel">Israel</option>
                            <option value="10440" title="Italy">Italy</option>
                            <option value="10441" title="Jamaica">Jamaica</option>
                            <option value="10442" title="Japan">Japan</option>
                            <option value="10443" title="Jersey">Jersey</option>
                            <option value="10444" title="Jordan">Jordan</option>
                            <option value="10445" title="Kazakhstan">Kazakhstan</option>
                            <option value="10446" title="Kenya">Kenya</option>
                            <option value="10447" title="Kiribati">Kiribati</option>
                            <option value="10448" title="Korea">Korea</option>
                            <option value="10449" title="Kuwait">Kuwait</option>
                            <option value="10450" title="Kyrgyzstan">Kyrgyzstan</option>
                            <option value="10451" title="Laos">Laos</option>
                            <option value="10452" title="Latvia">Latvia</option>
                            <option value="10453" title="Lebanon">Lebanon</option>
                            <option value="10454" title="Lesotho">Lesotho</option>
                            <option value="10455" title="Liberia">Liberia</option>
                            <option value="10456" title="Libya">Libya</option>
                            <option value="10457" title="Liechtenstein">Liechtenstein</option>
                            <option value="10458" title="Lithuania">Lithuania</option>
                            <option value="10459" title="Luxembourg">Luxembourg</option>
                            <option value="10460" title="Macao">Macao</option>
                            <option value="10461" title="Macedonia">Macedonia</option>
                            <option value="10462" title="Madagascar">Madagascar</option>
                            <option value="10463" title="Malawi">Malawi</option>
                            <option value="10464" title="Malaysia">Malaysia</option>
                            <option value="10465" title="Maldives">Maldives</option>
                            <option value="10466" title="Mali">Mali</option>
                            <option value="10467" title="Malta">Malta</option>
                            <option value="10468" title="Marshall island">Marshall island</option>
                            <option value="10469" title="Martinique">Martinique</option>
                            <option value="10470" title="Mauritania">Mauritania</option>
                            <option value="10471" title="Mauritius">Mauritius</option>
                            <option value="10472" title="Mayotte">Mayotte</option>
                            <option value="10473" title="Mexico">Mexico</option>
                            <option value="10474" title="Micronesia">Micronesia</option>
                            <option value="10475" title="Minor Outl.Ins.">Minor Outl.Ins.</option>
                            <option value="10476" title="Moldova, Repub.">Moldova, Repub.</option>
                            <option value="10477" title="Monaco">Monaco</option>
                            <option value="10478" title="Mongolia">Mongolia</option>
                            <option value="10479" title="Montenegro">Montenegro</option>
                            <option value="10480" title="Montserrat">Montserrat</option>
                            <option value="10481" title="Morocco">Morocco</option>
                            <option value="10482" title="Mozambique">Mozambique</option>
                            <option value="10483" title="N.Mariana Islnd">N.Mariana Islnd</option>
                            <option value="10484" title="Namibia">Namibia</option>
                            <option value="10485" title="Nauru">Nauru</option>
                            <option value="10486" title="Nepal">Nepal</option>
                            <option value="10487" title="Nether.Antilles">Nether.Antilles</option>
                            <option value="10488" title="Netherlands">Netherlands</option>
                            <option value="10489" title="New Caledonia">New Caledonia</option>
                            <option value="10491" title="Nicaragua">Nicaragua</option>
                            <option value="10492" title="Niger">Niger</option>
                            <option value="10493" title="Nigeria">Nigeria</option>
                            <option value="10494" title="Niue">Niue</option>
                            <option value="10495" title="Norfolk Island">Norfolk Island</option>
                            <option value="10498" title="Pakistan">Pakistan</option>
                            <option value="10499" title="Palau">Palau</option>
                            <option value="10500" title="Palestinian Territory">Palestinian Territory</option>
                            <option value="10501" title="Panama">Panama</option>
                            <option value="10502" title="Papua Nw Guinea">Papua Nw Guinea</option>
                            <option value="10503" title="Paraguay">Paraguay</option>
                            <option value="10504" title="Peru">Peru</option>
                            <option value="10505" title="Philippines">Philippines</option>
                            <option value="10506" title="Pitcairn">Pitcairn</option>
                            <option value="10507" title="Poland">Poland</option>
                            <option value="10508" title="Portugal">Portugal</option>
                            <option value="10509" title="Puerto Rico">Puerto Rico</option>
                            <option value="10510" title="Qatar">Qatar</option>
                            <option value="10511" title="Réunion">Réunion</option>
                            <option value="10512" title="Romania">Romania</option>
                            <option value="10513" title="Russian Fed.">Russian Fed.</option>
                            <option value="10514" title="Rwanda">Rwanda</option>
                            <option value="10515" title="S.Tome,Principe">S.Tome,Principe</option>
                            <option value="10516" title="Samoa">Samoa</option>
                            <option value="10517" title="San Marino">San Marino</option>
                            <option value="10518" title="Saudi Arabia">Saudi Arabia</option>
                            <option value="10519" title="Senegal">Senegal</option>
                            <option value="10520" title="Serbia">Serbia</option>
                            <option value="10521" title="Seychelles">Seychelles</option>
                            <option value="10656" title="SGeorgia&amp;SS Isl">SGeorgia&amp;SS Isl</option>
                            <option value="10523" title="Sierra Leone">Sierra Leone</option>
                            <option value="10524" title="Singapore">Singapore</option>
                            <option value="10525" title="Slovakia">Slovakia</option>
                            <option value="10526" title="Slovenia">Slovenia</option>
                            <option value="10527" title="Solomon Islands">Solomon Islands</option>
                            <option value="10528" title="Somalia">Somalia</option>
                            <option value="10529" title="South Africa">South Africa</option>
                            <option value="10530" title="Spain">Spain</option>
                            <option value="10531" title="Sri Lanka">Sri Lanka</option>
                            <option value="10532" title="St. Helena">St. Helena</option>
                            <option value="10533" title="St. Lucia">St. Lucia</option>
                            <option value="10534" title="St. Vincent">St. Vincent</option>
                            <option value="10657" title="St.Kitts&amp;Nevis">St.Kitts&amp;Nevis</option>
                            <option value="10536" title="St.Pier,Miquel.">St.Pier,Miquel.</option>
                            <option value="10537" title="Suriname">Suriname</option>
                            <option value="10658" title="Svalbard &amp;JMIsl">Svalbard &amp;JMIsl</option>
                            <option value="10539" title="Swaziland">Swaziland</option>
                            <option value="10540" title="Sweden">Sweden</option>
                            <option value="10541" title="Switzerland">Switzerland</option>
                            <option value="10542" title="Taiwan">Taiwan</option>
                            <option value="10543" title="Tajikistan">Tajikistan</option>
                            <option value="10544" title="Tanzania">Tanzania</option>
                            <option value="10545" title="Thailand">Thailand</option>
                            <option value="10546" title="Timor-Leste">Timor-Leste</option>
                            <option value="10547" title="Togo">Togo</option>
                            <option value="10548" title="Tokelau">Tokelau</option>
                            <option value="10549" title="Tonga">Tonga</option>
                            <option value="10550" title="Trinidad,Tobago">Trinidad,Tobago</option>
                            <option value="10551" title="Tunisia">Tunisia</option>
                            <option value="10552" title="Turkey">Turkey</option>
                            <option value="10553" title="Turkmenistan">Turkmenistan</option>
                            <option value="10659" title="Turks&amp;Caicos Is">Turks&amp;Caicos Is</option>
                            <option value="10555" title="Tuvalu">Tuvalu</option>
                            <option value="10556" title="Uganda">Uganda</option>
                            <option value="10557" title="Ukraine">Ukraine</option>
                            <option value="10558" title="Unit.Arab Emir.">Unit.Arab Emir.</option>
                            <option value="10559" title="United Kingdom">United Kingdom</option>
                            <option value="10560" title="Uruguay">Uruguay</option>
                            <option value="10561" title="US Virgin Is.">US Virgin Is.</option>
                            <option value="10562" title="Uzbekistan">Uzbekistan</option>
                            <option value="10563" title="Vanuatu">Vanuatu</option>
                            <option value="10564" title="Vatican City St">Vatican City St</option>
                            <option value="10565" title="Venzuela">Venzuela</option>
                            <option value="10566" title="Vietnam">Vietnam</option>
                            <option value="10567" title="Wallis,Futuna">Wallis,Futuna</option>
                            <option value="10568" title="Western Sahara">Western Sahara</option>
                            <option value="10569" title="Yemen">Yemen</option>
                            <option value="10570" title="Zambia">Zambia</option>
                            <option value="10571" title="Zimbabwe">Zimbabwe</option>
                        </select>
                    </div>
                    <div id="stateRow" class="signup-form-field"><span class="label">STATE</span>
                        <select id="state" class="value"></select>
                    </div>
                    <div class="signup-form-field"><span class="label">PHONE</span>
                        <input class="value" type="text" id="phone" />
                    </div>
                    <div class="signup-heading cycling-info">CYCLING INFO</div>
                    <div class="signup-form-field"><span class="label">EXP.</span>
                        <select class="value" id="exp">
                            <option value="">-- Please Select --</option>
                        </select>
                    </div>
                    <div class="signup-form-field"><span class="label">HEIGHT</span>
                        <select class="value" id="bikesize">
                            <option value="">-- Please Select --</option>
                        </select>
                    </div>
                    <div class="signup-form-field"><span class="label">PEDAL</span>
                        <select class="value" id="pedal">
                            <option value="">-- Please Select --</option>
                        </select>
                    </div>
                    <div class="save">SAVE TO PROFILE</div>
                </div>
            </div>
