package com.mollin.yapi.socket;

import com.mollin.yapi.exception.YeelightSocketException;
import org.pmw.tinylog.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class YeelightSocketHolder {
    private static int SOCKET_TIMEOUT = 1000;
    private String ip;
    private int port;
    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;

    public YeelightSocketHolder(String ip, int port) throws YeelightSocketException {
        this.ip = ip;
        this.port = port;
        this.initSocketAndStreams();
    }

    private void initSocketAndStreams() throws YeelightSocketException {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            this.socket = new Socket();
            this.socket.connect(inetSocketAddress, YeelightSocketHolder.SOCKET_TIMEOUT);
            this.socket.setSoTimeout(SOCKET_TIMEOUT);
            this.socketReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.socketWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception e) {
            throw new YeelightSocketException(e);
        }
    }

    public void send(String datas) throws YeelightSocketException {
        try {
            Logger.debug("{} sent to {}:{}", datas, this.ip, this.port);
            this.socketWriter.write(datas);
            this.socketWriter.flush();
        } catch (Exception e) {
            throw new YeelightSocketException(e);
        }
    }

    public String readLine() throws YeelightSocketException {
        try {
            String datas = this.socketReader.readLine();
            Logger.debug("{} received from {}:{}", datas, this.ip, this.port);
            return datas;
        } catch (Exception e) {
            throw new YeelightSocketException(e);
        }
    }
}
