package org.example.socket;

import org.example.service.ReservationService;
import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ReservationService service;
    private final String clientId;

    public ClientHandler(Socket socket, ReservationService service) {
        this.socket = socket;
        this.service = service;
        this.clientId = "ID-" + UUID.randomUUID().toString().substring(0, 5);
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("SERVER: Conectat! ID: " + clientId);

            String input;
            while ((input = in.readLine()) != null) {
                String[] parts = input.split(" ");
                String command = parts[0].toUpperCase();

                switch (command) {
                    case "LIST":
                        String all = service.getAllReservations().stream()
                                .map(r -> r.timeSlot).collect(Collectors.joining(", "));
                        out.println("SERVER: Sloturi: " + (all.isEmpty() ? "niciunul" : all));
                        break;
                    case "RESERVE":
                        if (parts.length < 2) { out.println("SERVER: Specifica ora!"); break; }
                        out.println(service.createReservation(clientId, parts[1]) ? "SERVER: Succes!" : "SERVER: Ocupat!");
                        break;
                    case "MY":
                        String mine = service.getMyReservations(clientId).stream()
                                .map(r -> r.timeSlot).collect(Collectors.joining(", "));
                        out.println("SERVER: Ale tale: " + (mine.isEmpty() ? "niciuna" : mine));
                        break;
                    case "CANCEL":
                        if (parts.length < 2) { out.println("SERVER: Specifica ora!"); break; }
                        out.println(service.cancelReservation(clientId, parts[1]) ? "SERVER: Anulat!" : "SERVER: Inexistenta!");
                        break;
                    case "EXIT":
                        out.println("SERVER: La revedere!");
                        return;
                    default:
                        out.println("SERVER: Comenzi: LIST, RESERVE, MY, CANCEL, EXIT");
                }
            }
        } catch (IOException e) {
            System.err.println("Deconectat: " + clientId);
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}