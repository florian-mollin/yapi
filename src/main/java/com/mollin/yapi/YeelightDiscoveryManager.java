package com.mollin.yapi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

public class YeelightDiscoveryManager {
    private final static String NEWLINE = "\r\n";

    private final static String SEARCH_REQUEST_ADDRESS = "239.255.255.250";
    private final static int SEARCH_REQUEST_PORT = 1982;
    private final static int RESPONSE_TIMEOUT = 1000;

    public static Set<YeelightDeviceMeta> search() throws IOException {
        final String request = "M-SEARCH * HTTP/1.1"    + NEWLINE +
                               "MAN: \"ssdp:discover\"" + NEWLINE +
                               "ST: wifi_bulb"          + NEWLINE;
        final byte[] requestBytes = request.getBytes();

        HashSet<YeelightDeviceMeta> devices = new HashSet<>(); // prevent duplicates as a result of multiple responses from the same device

        InetSocketAddress address = new InetSocketAddress(SEARCH_REQUEST_ADDRESS, SEARCH_REQUEST_PORT);
        DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length, address);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(RESPONSE_TIMEOUT);
        socket.send(requestPacket);
        try {
            while(true) {
                byte[] responseBuffer = new byte[1500];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);
                YeelightDeviceMeta device = processResponse(new String(responseBuffer, 0, responsePacket.getLength()));
                devices.add(device);
            }
        } catch (SocketTimeoutException e) {
            // signifies that all responses have been processed
        }
        return devices;
    }

    private static YeelightDeviceMeta processResponse(String response) {
        // TODO response validation
        YeelightDeviceMeta meta = new YeelightDeviceMeta();
        String[] lines = response.split(NEWLINE);
        for(String line : lines) {
            if(line.startsWith("Location:")) {
                final int prefixLength = "Location: yeelight://".length();
                String[] ipPortPair = line.substring(prefixLength).split(":");
                meta.ip = ipPortPair[0];
                meta.port = Integer.parseInt(ipPortPair[1]);
                continue;
            }
            if(line.startsWith("id:")) {
                final int prefixLength = "id: ".length();
                meta.id = line.substring(prefixLength);
                continue;
            }
            if(line.startsWith("model:")) {
                final int prefixLength = "model: ".length();
                meta.model = line.substring(prefixLength);
                continue;
            }
            if(line.startsWith("fw_ver:")) {
                final int prefixLength = "fw_ver: ".length();
                meta.firmware = line.substring(prefixLength);
                continue;
            }
            if(line.startsWith("support:")) {
                final int prefixLength = "support: ".length();
                meta.capabilities = line.substring(prefixLength).split(" ");
                continue;
            }
        }
        return meta;
    }
}
