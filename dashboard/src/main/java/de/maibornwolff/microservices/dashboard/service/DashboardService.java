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
import static de.maibornwolff.microservices.dashboard.event.RoomEventType.ENTER;

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
@Component
public class DashboardService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private RoomCountRepository roomCountRepository;


    /**
     * Methode wird von RabbitMQ bei Eintreffen einer RoomEvent-Nachricht aufgerufen.
     *
     * @param roomEvent
     */
    public void onRoomEvent(RoomEvent roomEvent) {
        LOGGER.info("Received RoomEvent: " + roomEvent);
        /*
        HANDSON - 1. Runde
        - Validiere roomEvent (es muss ein Room vorhanden sein)
            - Wenn roomEvent.getRoom() != null
                -> Speichere roomEvent im Repository
            - this.saveRoomEvent(...)
         */
        //
        if (roomEvent.getRoom() == null || roomEvent.getBadgeNumber() == null
                || roomEvent.getType() == null) {
            LOGGER.error("No valid Room received!");
            return;
        }
        saveRoomEvent(roomEvent);
        //
    }


    private void saveRoomEvent(RoomEvent roomEvent) {
        RoomCount roomCount = getCurrentRoomCountFromDB(roomEvent.getRoom());

        /*
        HANDSON - 2. Runde
         - RoomCount Objekt aktualisieren - bei ENTER +1, bei LEAVE -1
             - roomCount.setCount(...)
         - Aktualisiertes RoomCount Objekt in this.roomCountRepository speichern.
         */
        //
        int newCount = roomEvent.getType() == ENTER ? roomCount.getCount() + 1 : roomCount.getCount() - 1;
        roomCount.setCount(newCount);
        roomCountRepository.save(roomCount);
        //

        LOGGER.info("Saved RoomEvent: " + roomEvent.getType() + ", new count: " + roomCount.getCount());
    }


    /**
     * Load or create RoomCount DB object
     *
     * @param room
     * @return
     */
    private RoomCount getCurrentRoomCountFromDB(Room room) {
        RoomCount roomCount = roomCountRepository.findOne(room.getRoomNumber());
        if (roomCount == null) {
            LOGGER.warn("No CoomCount for Room " + room.getName() + " found. Creating one!");
            roomCount = new RoomCount(room.getRoomNumber(), room.getName());
            roomCount = roomCountRepository.save(roomCount);
        }
        return roomCount;
    }

    public List<RoomCount> getRoomCounts() {
        return roomCountRepository.findAll();
    }
}
