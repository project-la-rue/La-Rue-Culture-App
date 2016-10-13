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

package com.adobe.larue.dps2015.utils;

import org.apache.commons.io.IOUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class SSLUtils {
    public static String get(String url, Map<String, String> headers) {
        return get(url, headers, false);
    }

    public static String get(String url, Map<String, String> headers, boolean ignoreCertificateCheck) {
        HttpURLConnection connection = null;
        try {
            connection = getConnectionFromURL(url, ignoreCertificateCheck);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            return IOUtils.toString(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, String> headers, String data) {
        return post(url, headers, data, false);
    }

    public static String post(String url, Map<String, String> headers, String data, boolean ignoreCertificateCheck) {
        HttpURLConnection connection = null;
        try {
            connection = getConnectionFromURL(url, ignoreCertificateCheck);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            IOUtils.write(data, connection.getOutputStream());
            return IOUtils.toString(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static HttpURLConnection getConnectionFromURL(String url, boolean ignoreCertificateCheck) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        if (url.startsWith("https://") && ignoreCertificateCheck) {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, ignoreCertificateCheck ? getTrustManager() : null, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Create connection
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            // Tell the url connection object to use our socket factory which bypasses security checks
            connection.setSSLSocketFactory(sslSocketFactory);
            connection.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            return connection;
        } else {
            return (HttpURLConnection)new URL(url).openConnection();
        }
    }

    private static TrustManager[] getTrustManager() {
        return
                new TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }};

    }
}
