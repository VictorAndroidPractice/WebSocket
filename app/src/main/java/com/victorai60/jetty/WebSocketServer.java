package com.victorai60.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.IOException;
import java.io.InputStream;

class WebSocketServer {
    private static final int WEB_SOCKET_PORT = 8089;

    private static final WebSocketServer INSTANCE = new WebSocketServer();
    private Server mServer = new Server();

    private WebSocketServer() {

    }

    static WebSocketServer getInstance() {
        return INSTANCE;
    }

    void startServer() {
        SslSelectChannelConnector sslConnector = new SslSelectChannelConnector();
        sslConnector.setPort(WEB_SOCKET_PORT);
        sslConnector.setIncludeCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
                "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
                "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
                "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
                "TLS_RSA_WITH_AES_128_CBC_SHA256",
                "TLS_RSA_WITH_AES_128_CBC_SHA",
                "TLS_RSA_WITH_AES_256_CBC_SHA256",
                "TLS_RSA_WITH_AES_256_CBC_SHA"});
        InputStream in = null;
        try {
            in = MyApplication.application.getResources().getAssets().open("menpuji.bks");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SslContextFactory sslContextFactory = sslConnector.getSslContextFactory();
        sslContextFactory.setKeyStoreType("BKS");
        sslContextFactory.setKeyStoreInputStream(in);
        sslContextFactory.setKeyStorePassword("qazwsx1234");
        sslContextFactory.setKeyManagerPassword("qazwsx1234");
        sslContextFactory.setTrustStorePassword("qazwsx1234");
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("/");
        webSocketHandler.setHandler(resourceHandler);
        mServer.addConnector(sslConnector);
        mServer.setHandler(webSocketHandler);
        try {
            mServer.start();
            mServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
