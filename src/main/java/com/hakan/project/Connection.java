package com.hakan.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    String ip;
    int port;

    public Connection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Connection(int port) {
        this.port = port;
    }

    public void waitConnection() throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            DataInputStream dataInputStream;
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            System.out.println(dataInputStream.read());
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void connect(int value) throws IOException {
        Socket serverSocket = new Socket(ip, port);
        DataOutputStream dataOutputStream;
        dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
        dataOutputStream.write(value);
        serverSocket.close();
    }

    public void message(){
        new Thread() {
            public void run(){
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket = serverSocket.accept();
                    DataInputStream dataInputStream;
                    dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    Square.message=dataInputStream.read();
                    GameController.message=Square.message;
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
