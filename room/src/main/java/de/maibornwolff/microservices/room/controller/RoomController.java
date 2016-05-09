package de.maibornwolff.microservices.room.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.microservices.room.event.DeviceEvent;
import de.maibornwolff.microservices.room.exception.NotFoundException;
import de.maibornwolff.microservices.room.model.Room;
import de.maibornwolff.microservices.room.repository.RoomRepository;
import de.maibornwolff.microservices.room.service.RoomService;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@RestController
@RequestMapping(value = "/room")
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService;


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Room saveRoom(@RequestBody Room room) {
        return roomRepository.saveRoom(room);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value="{roomNumber}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Room getRoomByRoomNumber(@PathVariable String roomNumber) {
        Room room = roomRepository.findOne(roomNumber);
        if (room != null) {
            return room;
        }else{
            throw new NotFoundException("No room with number " + roomNumber + " found.");
        }
    }

    @RequestMapping(
            value="deviceevent",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void simulateDeviceEvent(@RequestBody DeviceEvent deviceEvent) {
        LOGGER.info("Simulating Device-Event: " + deviceEvent);
        roomService.onDeviceEvent(deviceEvent);
    }
}
