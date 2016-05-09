package de.maibornwolff.microservices.room.event;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public class DeviceEvent {
    private String deviceId;
    private String badgeNumber;
    private String roomNumber;


    public DeviceEvent() {
    }


    public DeviceEvent(String deviceId, String badgeNumber) {
        this.deviceId = deviceId;
        this.badgeNumber = badgeNumber;
    }


    public DeviceEvent(String deviceId, String badgeNumber, String roomNumber) {
        this.deviceId = deviceId;
        this.badgeNumber = badgeNumber;
        this.roomNumber = roomNumber;
    }


    public String getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getBadgeNumber() {
        return badgeNumber;
    }


    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }


    public String getRoomNumber() {
        return roomNumber;
    }


    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }


    @Override
    public String toString() {
        return "DeviceEvent{" +
                "deviceId='" + deviceId + '\'' +
                ", badgeNumber='" + badgeNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
