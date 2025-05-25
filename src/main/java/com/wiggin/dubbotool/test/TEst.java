package com.wiggin.dubbotool.test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @description:
 * @author: wiggin
 * @date: 2024-11-02 13:41
 **/
public class TEst {
    public static void main(String[] args) {
        String url = null;

        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();

            System.out.println("IP: " + host);
            System.out.println("Port: " + port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
