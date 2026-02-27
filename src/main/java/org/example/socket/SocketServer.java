package org.example.socket;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.example.service.ReservationService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@ApplicationScoped
public class SocketServer {

    @Inject
    ReservationService reservationService;

    void onStart(@Observes StartupEvent ev) {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        int port = 8081;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serverul de rezervari a pornit pe portul " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, reservationService)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}