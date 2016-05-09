package de.maibornwolff.microservices.room.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import de.maibornwolff.microservices.room.model.Room;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class RoomRepository {

    private Map<String, Room> rooms = new HashMap<>();

    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    public Room findOne(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Room saveRoom(Room room) {
        if (room.getRoomNumber() == null) {
            throw new IllegalArgumentException("RoomNumber needs to be filled");
        }

        rooms.put(room.getRoomNumber(), room);
        return room;
    }

    @PostConstruct
    public void init() {
        saveRoom(new Room("1", "Muenchen", "EG", "Hinten links"));
        saveRoom(new Room("2", "Nuernberg", "EG", "Hinten rechts"));
        saveRoom(new Room("3", "Regensburg", "EG", "Vorne links"));
        saveRoom(new Room("4", "Augsburg", "EG", "Vorne rechts"));
    }
}
