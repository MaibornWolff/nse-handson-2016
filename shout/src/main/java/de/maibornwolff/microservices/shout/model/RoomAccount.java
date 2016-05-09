package de.maibornwolff.microservices.shout.model;

/**
 * Created by Andreas Jochem, MaibornWolff GmbH on 05.05.16.
 */

import org.springframework.data.annotation.Id;

public class RoomAccount {

    @Id
    private String badgeNumber;
    private String firstName;
    private String lastName;
    private String roomName;


    public RoomAccount() {

    }


    public RoomAccount(String badgeNumber, String firstName, String lastName, String roomName) {
        this.badgeNumber = badgeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomName = roomName;
    }


    public String getBadgeNumber() {
        return badgeNumber;
    }


    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getRoomName() {
        return roomName;
    }


    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    @Override
    public String toString() {
        return "RoomAccount{" +
                "badgeNumber='" + badgeNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
