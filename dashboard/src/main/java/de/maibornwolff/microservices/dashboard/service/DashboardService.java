package de.maibornwolff.microservices.dashboard.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.maibornwolff.microservices.dashboard.event.RoomEvent;
import de.maibornwolff.microservices.dashboard.model.Room;
import de.maibornwolff.microservices.dashboard.model.RoomCount;
import de.maibornwolff.microservices.dashboard.repository.RoomCountRepository;

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
@Component
public class DashboardService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private RoomCountRepository roomCountRepository;


    /**
     * Methode wird von RabbitMQ bei Eintreffen einer neuen RoomEvent-Nachricht aufgerufen.
     */
    public void onRoomEvent(RoomEvent roomEvent) {
        LOGGER.info("Received RoomEvent: " + roomEvent);
        /*
        HANDSON - 1. Runde
        - Validiere Raum-Ereignis (es muss einen Raum enthalten)
            - Wenn Raum vorhanden (roomEvent.getRoom() != null und roomEvent.getBadgeNumber() != null und roomEvent.getType() != null)
                    -> Speichere 'roomEvent' im Repository ( this.saveRoomEvent(...); )
            - Wenn Raum nicht vorhanden -> Error loggen ( LOGGER.error(...); )
         */
        //

        //
    }

    /**
     * Raum-Zähler aufgrund des neuen Ereignisses aktualisieren und speichern
     */
    private void saveRoomEvent(RoomEvent roomEvent) {
        RoomCount roomCount = getCurrentRoomCountFromDB(roomEvent.getRoom());

        /*
        HANDSON - 2. Runde
         - Raum-Zähler aus der Datenbank laden
         - Raum-Zähler aktualisieren - bei ENTER +1, bei LEAVE -1 ( roomCount.setCount(...); )
         - Aktualisierten Raum-Zähler in der Datenbank (this.roomCountRepository) speichern.
         */
        //

        //

        LOGGER.info("Saved RoomEvent: " + roomEvent.getType() + ", new count: " + roomCount.getCount());
    }


    /**
     * Raum-Zähler aus der Datenbank laden oder einen neuen erstellen wenn Raum noch nicht bekannt
     */
    private RoomCount getCurrentRoomCountFromDB(Room room) {
        RoomCount roomCount = roomCountRepository.findOne(room.getRoomNumber());
        if (roomCount == null) {
            LOGGER.warn("No RoomCount for Room " + room.getName() + " found. Creating one!");
            roomCount = new RoomCount(room.getRoomNumber(), room.getName());
            roomCount = roomCountRepository.save(roomCount);
        }
        return roomCount;
    }

    public List<RoomCount> getRoomCounts() {
        return roomCountRepository.findAll();
    }
}
