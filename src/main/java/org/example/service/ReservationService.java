package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.example.entity.Reservation;
import java.util.List;

@ApplicationScoped
public class ReservationService {

    @Transactional
    public boolean createReservation(String clientId, String timeSlot) {
        if (Reservation.find("timeSlot", timeSlot).firstResult() != null) {
            return false;
        }
        Reservation r = new Reservation();
        r.clientId = clientId;
        r.timeSlot = timeSlot;
        r.persist();
        return true;
    }

    @Transactional
    public List<Reservation> getAllReservations() {
        return Reservation.listAll();
    }

    @Transactional
    public List<Reservation> getMyReservations(String clientId) {
        return Reservation.list("clientId", clientId);
    }

    @Transactional
    public boolean cancelReservation(String clientId, String timeSlot) {
        return Reservation.delete("clientId = ?1 and timeSlot = ?2", clientId, timeSlot) > 0;
    }
}