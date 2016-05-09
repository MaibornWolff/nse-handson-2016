package de.maibornwolff.microservices.device;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RabbitMQAdapter rabbitMQAdapter;


    public void assignDevice(Device device) {
        LOGGER.warn("Not saving - Devices are hardcoded!");
    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }


    /**
     * forward Event via Rabbit
     * @param deviceEvent
     */
    public void processEvent(DeviceEvent deviceEvent) {
        /*
        HANDSON - 2. Runde
        - RoomNumber zum Device ermitteln (this.getRoomNumber(...))
        - DeviceEvent um RoomNumber anreichern
        - this.rabbitMQAdapter aufrufen, um DeviceEvent weiter zu senden.
         */
        //
        String deviceRoomNumber = getRoomNumber(deviceEvent);
        deviceEvent.setRoomNumber(deviceRoomNumber);
        rabbitMQAdapter.sendDeviceEvent(deviceEvent);
        //
    }

    private String getRoomNumber(DeviceEvent deviceEvent) {
        return deviceRepository.findOne(deviceEvent.getDeviceId()).getRoomNumber();
    }
}
