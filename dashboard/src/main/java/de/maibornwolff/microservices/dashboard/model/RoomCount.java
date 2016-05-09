package de.maibornwolff.microservices.dashboard.model;

import org.springframework.data.annotation.Id;

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
public class RoomCount {

    @Id
    private String roomNumber;
    private String roomName;
    private int count = 0;

    public RoomCount() {
    }

    public RoomCount(String roomNumber, String roomName) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
    }


    public RoomCount(String roomNumber, String roomName, int count) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.count = count;
    }


    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RoomCount{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomName='" + roomName + '\'' +
                ", count=" + count +
                '}';
    }
}
