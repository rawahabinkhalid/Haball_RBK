
package com.haball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;


import com.android.volley.toolbox.HurlStack;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSL_HandShake {
    Context context;

    public void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
//
//    public HurlStack handleSSLHandshakeDistributor(Context context) {
//        this.context = context;
//        HurlStack hurlStack = new HurlStack() {
//            @Override
//            protected HttpURLConnection createConnection(URL url) throws IOException {
//                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
//                try {
//                    httpsURLConnection.setSSLSocketFactory(getSSLSocketFactoryDistributor());
//                    httpsURLConnection.setHostnameVerifier(getHostnameVerifierDistributor());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return httpsURLConnection;
//            }
//        };
//        return hurlStack;
//    }
//
//    private HostnameVerifier getHostnameVerifierDistributor() {
//        return new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
//                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                return hv.verify("retailer.haball.pk", session);
//            }
//        };
//    }
//    private TrustManager[] getWrappedTrustManagersDistributor(TrustManager[] trustManagers) {
//        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
//        return new TrustManager[]{
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return originalTrustManager.getAcceptedIssuers();
//                    }
//
//                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkClientTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkClientTrusted", e.toString());
//                        }
//                    }
//
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkServerTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkServerTrusted", e.toString());
//                        }
//                    }
//                }
//        };
//    }
//
//    private SSLSocketFactory getSSLSocketFactoryDistributor()
//            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream caInput = context.getResources().openRawResource(R.raw.haball_cert); // this cert file stored in \app\src\main\res\raw folder path
//
//        Certificate ca = cf.generateCertificate(caInput);
//        caInput.close();
//
//        KeyStore keyStore = KeyStore.getInstance("BKS");
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        TrustManager[] wrappedTrustManagers = getWrappedTrustManagersDistributor(tmf.getTrustManagers());
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, wrappedTrustManagers, null);
//
//        return sslContext.getSocketFactory();
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public HurlStack handleSSLHandshake(Context context) {
//        this.context = context;
//        HurlStack hurlStack = new HurlStack() {
//            @Override
//            protected HttpURLConnection createConnection(URL url) throws IOException {
//                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
//                try {
//                    httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
//                    httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return httpsURLConnection;
//            }
//        };
//        return hurlStack;
//    }
//
//    private HostnameVerifier getHostnameVerifier() {
//        return new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
//                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                return hv.verify("175.107.203.97", session);
//            }
//        };
//    }
//
//    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
//        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
//        return new TrustManager[]{
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return originalTrustManager.getAcceptedIssuers();
//                    }
//
//                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkClientTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkClientTrusted", e.toString());
//                        }
//                    }
//
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkServerTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkServerTrusted", e.toString());
//                        }
//                    }
//                }
//        };
//    }
//
//    private SSLSocketFactory getSSLSocketFactory()
//            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream caInput = context.getResources().openRawResource(R.raw.mycert); // this cert file stored in \app\src\main\res\raw folder path
//
//        Certificate ca = cf.generateCertificate(caInput);
//        caInput.close();
//
//        KeyStore keyStore = KeyStore.getInstance("BKS");
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, wrappedTrustManagers, null);
//
//        return sslContext.getSocketFactory();
//    }
}