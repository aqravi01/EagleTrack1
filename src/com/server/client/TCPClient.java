package com.server.client;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        
        Socket clientSocket = new Socket("localhost", 8008);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromUser = null;
        
        for(int i = 1; i<10; i++){
        	 inFromUser = new BufferedReader(new InputStreamReader(System.in));
             sentence = inFromUser.readLine();
             outToServer.writeBytes(sentence + '\n');
        }
       
        /*BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String modifiedSentence;
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);*/
        System.out.println("sent");
        clientSocket.close();
    }
}
