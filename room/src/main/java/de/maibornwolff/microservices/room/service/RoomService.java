package de.maibornwolff.microservices.room.service;

import com.google.common.annotations.VisibleForTesting;
import de.maibornwolff.microservices.room.event.DeviceEvent;
import de.maibornwolff.microservices.room.event.RoomEvent;
import de.maibornwolff.microservices.room.event.RoomEventType;
import de.maibornwolff.microservices.room.model.RoomAllocation;
import de.maibornwolff.microservices.room.repository.RoomAllocationRepository;
import de.maibornwolff.microservices.room.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class RoomService {

    private Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomAllocationRepository roomAllocationRepository;


    /**
     * Wird bei neuen DeviceEvents in der Queue aufgerufen
     */
    public void onDeviceEvent(DeviceEvent deviceEvent) {
        LOGGER.info("Received DeviceEvent: " + deviceEvent);

        /*
        HANDSON - 1. Runde

        - Wenn sich der Benutzer bereits im Raum vom Event befindet, nur einen LEAVE-Event senden:
            - this.sendLeaveEvent(...);
        - Ansonsten - LEAVE-Event fuer aktuellen Raum senden und ENTER-Event fuer neuen Raum senden:
            - this.sendLeaveEvent(...)
            - this.sendEnterEvent(...)

        Bitte beide Blöcke füllen
         */

        if (isUserInRoom(deviceEvent.getBadgeNumber(), deviceEvent.getRoomNumber())) {
            //

            //
        }else{
            //

            //
        }

    }

    /**
     * Raum verlassen
     */
    @VisibleForTesting
    protected void sendLeaveEvent(String badgeNumber) {
        RoomAllocation currentAllocation = roomAllocationRepository.findOne(badgeNumber);

        /*
        HANDSON - 2. Runde
        - Verarbeitung nur wenn der Benutzer sich gerade in einem Raum befindet ( currentAllocation != null ):
          - Aktuelle Belegung aus roomAllocationRepository entfernen
                - roomAllocationRepository.delete(...)
          - Raum-Ereignis ( RoomEvent ) erzeugen
                - this.buildRoomEvent(...)
                - RoomEventType.LEAVE
          - Raum-Ereignis per RabbitMQ senden
                - this.sendRoomEventToRabbitMQ(...)
         */
        //

        //
    }

    @VisibleForTesting
    protected void sendEnterEvent(String badgeNumber, String roomNumber) {
        LOGGER.trace("called sendEnterEvent");
        /*
        HANDSON - 3. Runde
        - Raum-Belegung erzeugen ( new RoomAllocation(...); )
        - Raum-Belegung in der Datenbank ( this.roomAllocationRepository ) speichern
        - Raum-Ereignis erzeugen
            - this.buildRoomEvent(...)
            - RoomEventType.ENTER
        - RoomEvent per RabbitMQ senden
            - this.sendRoomEventToRabbitMQ(...)
         */

        //

        //
    }

    private void sendRoomEventToRabbitMQ(RoomEvent roomEvent) {
        LOGGER.info("Sending " + roomEvent.getType() + "-Event " + roomEvent);
        rabbitTemplate.convertAndSend(roomEvent);
    }

    /**
     * Hilfsmethode, um Raum-Ereignis zu erzeugen
     */
    private RoomEvent buildRoomEvent(String roomNumber, String badgeNumber, RoomEventType type) {
        RoomEvent roomEvent = new RoomEvent();
        roomEvent.setRoom(roomRepository.findOne(roomNumber));
        roomEvent.setBadgeNumber(badgeNumber);
        roomEvent.setType(type);
        return roomEvent;
    }

    private boolean isUserInRoom(String badgeNumber, String roomNumber) {
        RoomAllocation currentAllocation = roomAllocationRepository.findOne(badgeNumber);
        return currentAllocation != null
                && roomNumber.equals(currentAllocation.getCurrentRoomNumber());
    }
}
