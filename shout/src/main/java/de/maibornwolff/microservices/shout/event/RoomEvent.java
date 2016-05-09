package de.maibornwolff.microservices.shout.event;

import de.maibornwolff.microservices.shout.model.Room;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public class RoomEvent {
    private Room room;
    private String badgeNumber;
    private RoomEventType type;


    public RoomEvent(Room room, String badgeNumber, RoomEventType type) {
        this.room = room;
        this.badgeNumber = badgeNumber;
        this.type = type;
    }


    public RoomEvent() {

    }


    public Room getRoom() {
        return room;
    }


    public void setRoom(Room room) {
        this.room = room;
    }


    public String getBadgeNumber() {
        return badgeNumber;
    }


    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }


    public RoomEventType getType() {
        return type;
    }


    public void setType(RoomEventType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "RoomEvent{" +
                "room=" + room +
                ", badgeNumber='" + badgeNumber + '\'' +
                ", type=" + type +
                '}';
    }
}
