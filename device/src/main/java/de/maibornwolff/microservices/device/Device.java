package de.maibornwolff.microservices.device;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
//@Document
public class Device {

//    @Id
    private String deviceId;
    private String roomNumber;


    public Device() {
    }


    public Device(String deviceId, String roomNumber) {
        this.deviceId = deviceId;
        this.roomNumber = roomNumber;
    }


    public String getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getRoomNumber() {
        return roomNumber;
    }


    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
