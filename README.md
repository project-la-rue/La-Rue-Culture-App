# Adobe La Rue Cultural App

This project is to create an AEM Mobile app that allows cyclists to view content and register for ride events. The project has the following aspects:

 - AEM Forms for creating article content and ride event content
 - AEM App for storing articles created by AEM Forms
 - AEM Mobile for managing articles uploaded from the AEM App
 - Strava Account for ride event route data
 - Google Map API for rendering ride event routes
 - JP Enterprise for event registration and participant management

## Prerequisites

 - JAVA 1.8
 - AEM 6.1
 - AEM-6.1-SERVICE-PACK-1
 - AEM-6.1 Apps Feature Pack 3

## Building

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package, and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

#### Using with VLT

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

#### Specifying CRX Host/Port

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>

## Setting Up on AEM

After the project is successfully built, [Configuring AEM to work with AEM Mobile](https://helpx.adobe.com/digital-publishing-solution/help/configure-aem.html)

## Content Creation

Once the project is setup on AEM, a user can start creating content.

#### Creating Articles on AEM
This project comes with two AEM Forms used for content submission. An AEM Workflow takes the submitted content from the forms and produces an AEM Mobile Article on AEM. The forms are located under:

	http://$(crx.host}:${crx.port}/aem/forms.html/content/dam/formsanddocuments/larue

##### Ride Event Schedule
The ``Ride Event Submission`` form is used for creating an article for a ride event with event schedules. To generate schedule content, upload a CSV file which is obtained from [JP Enterprises, an event registration portal](http://www.eventmarketingportal.com/). The CSV contains a list of participants and their registered event time slots. The time slots are defined in columns with the column name format ``item[x]``. This column name is used on the ``Source name`` field of the ``Enter rides information here`` section of the form to link a ride event time slot with a route. The route data is retrieved from [Strava](https://www.strava.com/). This project by default is using a demo Strava account. This section also leverages Google Map API to render the route on a map view. The Google Map API key is defined and can be changed under Cloud Services located at the following path from project root folder:
	
	content/src/main/content/jcr_root/etc/cloudservices/googlemapservice/larue_googlemap_config

After form submission, an article is created and can be uploaded to AEM Mobile from AEM App's dashboard.

##### Cofigure AEM Mobile On Deman API
Configure the following two places under ``http://$(crx.host}:${crx.port}/system/console/configMgr``
- com.adobe.larue.impl.servlets.RelatedArticlesServlet
- com.adobe.cq.mobile.dps.impl.service.AdobeDPSClient

##### Related Articles
Each article has the "Related Articles" section at the end. This section is configurable:
In its edit dialog:
- Enabled: flag if this section is enabled
- Server URL: ``http://$(crx.host}:${crx.port}/bin/larue/relatedArticles``
- Article Collection: the name of collection to which the related articles are searched under
- Article Name: the name of the current article
- Number of articles to retrieve: restricts number of results
- Error message: Message to be displayed when no articles are returned

##### Newsfeed
The existing Newsfeed article also leverages the Related Article functionality to display a list of articles as news feed. On [AEM Mobile](aemmobile.adobe.com), Create a collection named "Newsfeed". Any articles under this collection will be displayed on the Newsfeed article.

#### [More on AEM Mobile](https://helpx.adobe.com/digital-publishing-solution/topics.html)

## Want commit rights?

* Create an issue.

## Third Party Libraries
 - [Adobe Edge Web Fonts](https://edgewebfonts.adobe.com)
 - [Google Maps API](https://developers.google.com/maps)
 - [gson](https://github.com/google/gson)
 - [Moment.js](http://momentjs.com)
 - [PapaParse](https://github.com/mholt/PapaParse)
 - [SimpleWeather.js](https://github.com/monkeecreate/jquery.simpleWeather)
 - [Slick.js](http://kenwheeler.github.io/slick)
