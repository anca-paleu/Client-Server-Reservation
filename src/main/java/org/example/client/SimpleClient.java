package org.example.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8081);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String welcome = in.readLine();
            System.out.println(welcome);
            System.out.println("Comenzi: LIST, RESERVE [ora], MY, CANCEL [ora], EXIT");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();
                out.println(command);

                if (command.equalsIgnoreCase("EXIT")) break;

                String response = in.readLine();
                if (response != null) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare de conexiune: " + e.getMessage());
        }
    }
}