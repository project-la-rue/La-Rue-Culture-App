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

package com.adobe.larue.impl.workflows;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.webservicesupport.*;
import com.adobe.larue.dps2015.DpsClient;
import com.adobe.larue.dps2015.exceptions.DpsException;
import com.adobe.larue.dps2015.models.*;
import com.adobe.larue.impl.exceptions.InvalidParameterException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import java.net.URL;
import java.net.URLConnection;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

@Component(label = "La Rue DPS Article Workflow Process",
		metatype = true)
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "La Rue DPS Article workflow process implementation."),
        @Property(name = Constants.SERVICE_VENDOR, value = "Adobe"),
        @Property(name = "process.label", value = "La Rue DPS Article Workflow Process") })

public class ArticleCreationStep implements WorkflowProcess {
    final private static Logger log = LoggerFactory.getLogger(ArticleCreationStep.class);

    final static private String PROP_TEMPLATE = "template";
    final static private String PROP_TARGET = "target";
    final static private String PROP_TITLE = "dps-title";
    final static private String PROP_STATUS = "status";
    final static private String PROP_RESOURCE_TYPE = "sling:resourceType";
    final static private String PROP_FILE_REFERENCE = "fileReference";
    final static private String PROP_IMAGE_CROP = "imageCrop";
    final static private String PROP_IMAGE_ROTATE = "imageRotate";
    final static private String PROP_COLLECTION_NAME = "__collectionName";
    final static private String PROP_ARTICLE_TITLE = "title";
    final static private String PROP_ARTICLE_THUMBNAIL = "thumbnail";
    final static private String PROP_ARTICLE_NAME = "name";
    final static private String PROP_ARTICLE_CONTENT = "content";
    final static private String PROP_RELATED_ARTICLES_URL = "url";
    final static private String PROP_RELATED_ARTICLES_ARTICLE = "article";
    final static private String PROP_RELATED_ARTICLES_COLLECTION = "collection";

    final static private String NODE_IMAGE = "image";
    final static private String NODE_SOCIAL_SHARE_IMAGE = "social-share-image";
    final static private String NODE_RELATED_ARTICLES = "relatedarticles";

    final static private String SLING_FOLDER = "sling:Folder";

    final static private String STATUS_SUBMITTED = "submitted";
    final static private String STATUS_ARTICLE_CREATED = "created";

    final static private String ARTICLES_ROOT = "articles";
    final static private String ASSETS_ROOT = "/content/dam";
    final static private String ASSET_FOLDER_TYPE = "sling:OrderedFolder";
    final static private String CONTENT_FOLDER = "/content";

    final static private String DEFAULT_IMAGE_PATH = "/content/dam/geometrixx-outdoors/app/icons/icon.png";
    final static private String RESOURCE_IMAGE = "foundation/components/image";
    
    final static private String REPEATABLE_IDENTIFIER = "multifieldcount";
    
    final static private String YOUTUBE_URL_FIELD = "par.youtubevideo1.videoUrl";
    final static private String YOUTUBE_THUMBNAIL_FIELD = "par.youtubevideo1.thumbnail";
    final static private String IMAGE_BASE64_PREFIX_PART = "data:image/jpeg;base64,";
    final static private String YOUTUBE_THUMBNAIL_BASE_PATH = "http://img.youtube.com/vi/";
    final static private String YOUTUBE_THUMBNAIL_MEDIUM_QUALITY_DEFAULT = "/mqdefault.jpg";
    final static private String YOUTUBE_VIDEO_ID_REGEX = "^.*(youtu.be\\/|v\\/|u\\/\\w/|embed\\/|watch\\?v=|\\&v=)([^#\\&\\?]*).*";
    
    private static final String DEFAULT_SERVER_URL = "";
	private String serverUrl = DEFAULT_SERVER_URL;
	@Property(label = "Server URL",
			description = "Configures the URL for the related articles sling servlet",
			value = DEFAULT_SERVER_URL)
	public static final String PROPERTY_SERVER_URL = "serverUrl";
	
    @Activate
	protected final void activate(final Map<String, String> properties) throws Exception {
		this.serverUrl = PropertiesUtil.toString(properties.get(PROPERTY_SERVER_URL), DEFAULT_SERVER_URL);
	}
	
    @Reference
    private CryptoSupport cryptoSupportService;

    @Reference
    private ConfigurationAdmin configAdmin;

    protected ResourceResolver resourceResolver;
    protected Session session;

    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        log.info("La Rue DPS Article workflow process started.");
        try {
            initializeContext(workflowSession);

            if (!"JCR_PATH".equals(workItem.getWorkflowData().getPayloadType())) {
                throw new InvalidParameterException("Invalid payload type");
            }
            String path = workItem.getWorkflowData().getPayload().toString();
            if (!session.itemExists(path)) {
                throw new InvalidParameterException("Invalid path");
            }

            Node sourceNode = session.getNode(path);

            if (!sourceNode.hasProperty(PROP_STATUS) || !STATUS_SUBMITTED.equals(sourceNode.getProperty(PROP_STATUS).getString())) {
                throw new InvalidParameterException("Invalid node data status");
            }
            if (!sourceNode.hasProperty(javax.jcr.Property.JCR_DATA) || sourceNode.getProperty(javax.jcr.Property.JCR_DATA).getValue().getType() != PropertyType.BINARY) {
                throw new InvalidParameterException("Invalid node data");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(sourceNode.getProperty(javax.jcr.Property.JCR_DATA).getBinary().getStream());
            Element data = getElementByTagName(getElementByTagName(document.getDocumentElement(), "afUnboundData"), "data");
            if (data == null) {
                throw new InvalidParameterException("Invalid content");
            }

            Map<String, String> values = new HashMap<String, String>();
            NodeList list = data.getElementsByTagName("*");
            int numOfNodes = list.getLength();
            for (int i = 0; i < numOfNodes; ++i) {
                org.w3c.dom.Node n = list.item(i);
                if (n instanceof Element) {
                    Element e = (Element)n;
                    values.put(e.getTagName(), e.getTextContent().trim());
                }
            }

            String template = values.get(PROP_TEMPLATE);
            String target = values.get(PROP_TARGET);
            String title = values.get(PROP_TITLE);

            if (StringUtils.isEmpty(template) || !session.itemExists(template)) {
                throw new InvalidParameterException("Missing or invalid template");
            }
            if (StringUtils.isEmpty(target) || !target.startsWith(CONTENT_FOLDER) || !session.itemExists(target)) {
                throw new InvalidParameterException("Missing or invalid target");
            }
            if (StringUtils.isEmpty(title)) {
                throw new InvalidParameterException("Missing or invalid title");
            }
            Node appNode = session.getNode(target);
            Node articlesNode = appNode.hasNode(ARTICLES_ROOT) ? appNode.getNode(ARTICLES_ROOT) : appNode.addNode(ARTICLES_ROOT, NodeType.NT_FOLDER);

            String name = generateName(articlesNode, title);
            Page page = createPage(articlesNode.getPath(), name, template, title);

            String assetsPath = ASSETS_ROOT + target.substring(CONTENT_FOLDER.length()) + "/" + name;
            Node assetsNode = createOrGetNode(session, assetsPath, ASSET_FOLDER_TYPE);

            values.remove(PROP_TEMPLATE);
            values.remove(PROP_TARGET);
            values.remove(PROP_STATUS);

            String collection = values.get(PROP_COLLECTION_NAME);
            if (collection != null) {
                values.remove(PROP_COLLECTION_NAME);
                values.put(NODE_RELATED_ARTICLES + "." + PROP_RELATED_ARTICLES_COLLECTION, collection);
                values.put(NODE_RELATED_ARTICLES + "." + PROP_RELATED_ARTICLES_ARTICLE, name);
                values.put(NODE_RELATED_ARTICLES + "." + PROP_RELATED_ARTICLES_URL, serverUrl);
            }

            createArticle(sourceNode, session.getNode(page.getPath()).getNode(javax.jcr.Property.JCR_CONTENT), assetsNode, values);

            sourceNode.setProperty(PROP_STATUS, STATUS_ARTICLE_CREATED);

            session.save();
        } catch (Exception e) {
            logError(log, e);
        }
        log.info("La Rue DPS Article workflow process ended.");
    }

    protected Element getElementByTagName(Element parent, String name) {
        NodeList list = parent.getElementsByTagName(name);
        if (list != null && list.getLength() > 0) {
            return (Element)list.item(0);
        } else {
            return null;
        }
    }

    protected String generateName(Node node, String title) throws RepositoryException {
        String name = title.toLowerCase().replaceAll(" ", "-");
        if (node.hasNode(name)) {
            int counter = 1;
            while (node.hasNode(name + "_" + counter)) {
                counter++;
            }
            return name + "_" + counter;
        } else {
            return name;
        }
    }

    protected void initializeContext(WorkflowSession workflowSession) {
        resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
        session = workflowSession.adaptTo(Session.class);
    }

    protected List<DpsArticle> getRelatedArticles(Node appNode, String collectionName) throws RepositoryException, DpsException, CryptoException, IOException {
        List<DpsArticle> articles = new ArrayList<DpsArticle>();

        ConfigurationManager cm = resourceResolver.adaptTo(ConfigurationManager.class);
        Node contentNode = appNode.getNode(javax.jcr.Property.JCR_CONTENT);
        String configPath = contentNode.getProperty("dps-cloudConfig").getString();
        Configuration config = cm.getConfiguration(configPath);

        org.osgi.service.cm.Configuration adobeDpsClientConfig = configAdmin.getConfiguration("com.adobe.cq.mobile.dps.impl.service.AdobeDPSClient");
        Dictionary<String, Object> properties = adobeDpsClientConfig.getProperties();

        DpsConfiguration dpsConfig = new DpsConfiguration();
        dpsConfig.setClientId(properties.get("clientId").toString());
        dpsConfig.setClientSecret(properties.get("clientSecret").toString());
        String value = config.get("dpsDeviceId", "");
        if (cryptoSupportService.isProtected(value)) {
            value = cryptoSupportService.unprotect(value);
        }
        dpsConfig.setDeviceId(value);
        value = config.get("dpsDeviceToken", "");
        if (cryptoSupportService.isProtected(value)) {
            value = cryptoSupportService.unprotect(value);
        }
        dpsConfig.setDeviceToken(value);

        DpsProject dpsProject = new DpsProject();
        dpsProject.setId(contentNode.getProperty("dps-projectId").getString());
        dpsProject.setTitle(contentNode.getProperty("dps-projectTitle").getString());

        DpsClient dpsClient = new DpsClient(dpsConfig);
        DpsEntityListOptions options = new DpsEntityListOptions();
        options.getEntityTypes().add(DpsEntityType.Collection);
        DpsCollection dpsCollection = null;
        List<DpsEntity> entities;
        int pageNo = 0;
        do {
            options.setPageNo(pageNo);
            entities = dpsClient.getPagedEntities(dpsProject, options);
            for (DpsEntity entity : entities) {
                if (entity instanceof DpsCollection && entity.getEntityName().equals(collectionName)) {
                    dpsCollection = (DpsCollection) entity;
                    break;
                }
            }
            pageNo++;
        } while (dpsCollection == null && entities.size() == options.getPageSize());

        if (dpsCollection != null && dpsCollection.getContentElementsPath() != null) {
            entities = dpsClient.getEntities(dpsCollection.getContentElementsPath());
            for (DpsEntity entity : entities) {
                if (entity instanceof DpsArticle) {
                    entity.setPublished(dpsClient.getPublishTime(dpsProject, entity));
                    if (entity.getPublished() != null) {
                        articles.add((DpsArticle) entity);
                    }
                }
            }
            Collections.sort(articles, new Comparator<DpsArticle>() {
                @Override
                public int compare(DpsArticle o1, DpsArticle o2) {
                    return (int) (o2.getPublished().getTime() - o1.getPublished().getTime());
                }
            });
        }
        return articles;
    }

    protected Page createPage(String parent, String name, String template, String title) throws RepositoryException, WCMException {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        return pageManager.create(parent, name, template, title, true);
    }

    protected void createArticle(Node sourceNode, Node contentNode, Node assetNode, Map<String, String> values) throws RepositoryException {
        createImage(sourceNode, contentNode, assetNode, NODE_IMAGE);
        createImage(sourceNode, contentNode, assetNode, NODE_SOCIAL_SHARE_IMAGE);
        NodeIterator ni = sourceNode.getNodes();
        while (ni.hasNext()) {
            Node node = ni.nextNode();
            String nodeName = node.getName();
            if (!NODE_IMAGE.equals(nodeName) && !NODE_SOCIAL_SHARE_IMAGE.equals(nodeName) && SLING_FOLDER.equals(node.getPrimaryNodeType().getName())) {
                ArrayList<Asset> assets = createAssets(assetNode, node);
                int count = 1;
                for (Iterator<Asset> it = assets.iterator(); it.hasNext();) {
                    if(nodeName.contains(REPEATABLE_IDENTIFIER)){
                        //MULTI-FIELD
                        values.put(nodeName.replace(REPEATABLE_IDENTIFIER, Integer.toString(count)), it.next().getPath());
                        count++;
                    }else{
                        //SINGLE FIELD
                        values.put(nodeName, it.next().getPath());
                    }
                }
            }
        }
        for (Map.Entry<String,String> entry : values.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            writeValue(contentNode, name, value);
            if(name.equals(YOUTUBE_URL_FIELD)){
                //add extra node for base64 youtube thumbnail
                writeValue(contentNode, YOUTUBE_THUMBNAIL_FIELD, getBase64Image(value));
            }
        }
    }

    protected void createImage(Node sourceNode, Node contentNode, Node assetNode, String imageNodeName) throws RepositoryException {
        Node imageNode = contentNode.addNode(imageNodeName, NodeType.NT_UNSTRUCTURED);
        String imagePath = DEFAULT_IMAGE_PATH;
        if (sourceNode.hasNode(imageNodeName)) {
            ArrayList<Asset> assets = createAssets(assetNode, sourceNode.getNode(imageNodeName));
            if (assets != null && assets.size() > 0) {
                imagePath = assets.get(0).getPath();
            }
        }
        imageNode.setProperty(PROP_FILE_REFERENCE, imagePath);
        imageNode.setProperty(PROP_IMAGE_CROP, "");
        imageNode.setProperty(PROP_IMAGE_ROTATE, 0L);
        imageNode.setProperty(PROP_RESOURCE_TYPE, RESOURCE_IMAGE);
    }

    protected ArrayList<Asset> createAssets(Node assetRoot, Node fileNode) throws RepositoryException {
        ArrayList<Asset> result = new ArrayList<Asset>();
        AssetManager am = resourceResolver.adaptTo(AssetManager.class);
        NodeIterator ni = fileNode.getNodes();
        Node image = null;
        while (ni.hasNext()) {
            Node n = ni.nextNode();
            if (n.getPrimaryNodeType().isNodeType(NodeType.NT_FILE)) {
                String filename = getFilename(assetRoot, n.getName());
                Node content = n.getNode(javax.jcr.Property.JCR_CONTENT);
                result.add(am.createAsset(assetRoot.getPath() + "/" + filename, content.getProperty(javax.jcr.Property.JCR_DATA).getBinary().getStream(), content.getProperty(javax.jcr.Property.JCR_MIMETYPE).getString(), true));
            }
        }
        return result;
    }

    protected String getFilename(Node node, String name) throws RepositoryException {
        if (node.hasNode(name)) {
            int pos = name.lastIndexOf('.');
            String fileName = pos > 0 ? name.substring(0, pos) : name;
            fileName = fileName.replaceAll("\\s","_");
            String extName = pos > 0 ? name.substring(pos) : null;
            int counter = 1;
            while (node.hasNode(fileName + "_" + counter + (extName != null ? extName : ""))) {
                counter ++;
            }
            return fileName + "_" + counter + (extName != null ? extName : "");
        } else {
            return name.replaceAll("\\s","_");
        }
    }

    protected void writeValue(Node contentNode, String name, String value) throws RepositoryException {
        name = name.replaceAll("__", ":");
        name = name.replaceAll("\\.", "/");
        Node node = contentNode;
        int pos = name.lastIndexOf("/");
        if (pos > 0) {
            node = createOrGetNode(session, node.getPath() + "/" + name.substring(0, pos), NodeType.NT_UNSTRUCTURED);
            name = name.substring(pos + 1);
        }
        node.setProperty(name, value);
    }

    protected Node createOrGetNode(Session session, String path, String nodeType) throws RepositoryException {
        if (session.itemExists(path)) {
            return session.getNode(path);
        }
        String[] values = path.split("/");
        Node node = session.getRootNode();
        for (int i = 0; i < values.length; ++i) {
            String value = values[i].trim();
            if (value.length() > 0) {
                if (node.hasNode(value)) {
                    node = node.getNode(value);
                } else {
                    node = node.addNode(value, nodeType);
                }
            }
        }
        session.save();
        return node;
    }

    protected void logError(Logger log, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log.error(sw.toString());
    }
    
    private String getBase64Image(String videoUrl) {
        String imageDataString = "";
        try {           
            if(!StringUtils.isEmpty(videoUrl)){
                String imageUrl = getYoutubeVideoThumbnailUrl(videoUrl);
                URL url = new URL(imageUrl);
                InputStream is = url.openStream();
                byte[] bytes = IOUtils.toByteArray(is);

                // Converting Image byte array into Base64 String
                imageDataString = IMAGE_BASE64_PREFIX_PART + Base64.encodeBase64String(bytes);
            }
        } catch (FileNotFoundException e) {
            logError(log, e);
        } catch (IOException ioe) {
            logError(log, ioe);
        } finally {
            return imageDataString;
        }
    }
    
    private String getYoutubeVideoThumbnailUrl(String youtubeUrl) {
        String id = getYoutubeVideoId(youtubeUrl);
        return YOUTUBE_THUMBNAIL_BASE_PATH + id + YOUTUBE_THUMBNAIL_MEDIUM_QUALITY_DEFAULT;
    }
    
    private String getYoutubeVideoId(String youtubeUrl) {
        String id = "";
        Matcher matcher = Pattern.compile(YOUTUBE_VIDEO_ID_REGEX).matcher(youtubeUrl);
        while (matcher.find()) {
            if (matcher.group(2).length() == 11) {
                id = matcher.group(2);
                break;
            }
        }
        return id;
    }
}
