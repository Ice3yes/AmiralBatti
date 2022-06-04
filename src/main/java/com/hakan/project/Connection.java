package com.hakan.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;



    public void waitConnection(int port) throws IOException {
        new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket = serverSocket.accept();
                    dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void connect(String ip, int port) throws IOException {
        Socket serverSocket = new Socket(ip, port);
        dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
        dataInputStream = new DataInputStream(serverSocket.getInputStream());
    }

}
