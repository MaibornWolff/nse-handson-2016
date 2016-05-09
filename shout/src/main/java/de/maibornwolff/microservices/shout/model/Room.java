package de.maibornwolff.microservices.shout.model;


/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public class Room {

    private String roomNumber;
    private String name;
    private String floor;
    private String location;


    public Room() {
    }


    public Room(String roomNumber, String name, String floor, String location) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.floor = floor;
        this.location = location;
    }


    public String getRoomNumber() {
        return roomNumber;
    }


    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getFloor() {
        return floor;
    }


    public void setFloor(String floor) {
        this.floor = floor;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", name='" + name + '\'' +
                ", floor='" + floor + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
