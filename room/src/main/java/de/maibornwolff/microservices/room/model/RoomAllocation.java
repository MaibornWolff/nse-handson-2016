package de.maibornwolff.microservices.room.model;

import org.springframework.data.annotation.Id;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public class RoomAllocation {
    @Id
    private String badgeNumber;
    private String currentRoomNumber;


    public RoomAllocation() {
    }


    public RoomAllocation(String badgeNumber, String currentRoomNumber) {
        this.badgeNumber = badgeNumber;
        this.currentRoomNumber = currentRoomNumber;
    }


    public String getBadgeNumber() {
        return badgeNumber;
    }


    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }


    public String getCurrentRoomNumber() {
        return currentRoomNumber;
    }


    public void setCurrentRoomNumber(String currentRoomNumber) {
        this.currentRoomNumber = currentRoomNumber;
    }
}
