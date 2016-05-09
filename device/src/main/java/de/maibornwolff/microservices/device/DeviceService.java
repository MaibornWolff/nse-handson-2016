package de.maibornwolff.microservices.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * DeviceEvent anreichern und weiterleiten
     */
    public void processEvent(DeviceEvent deviceEvent) {

        /*
        HANDSON - 2. Runde
        - Raum-Nr zum Device ermitteln ( this.getRoomNumber(...); )
        - DeviceEvent um Raum-Nr anreichern
        - this.rabbitMQAdapter(...) aufrufen, um DeviceEvent weiter zu senden.
         */
        //

        //
    }

    private String getRoomNumber(DeviceEvent deviceEvent) {
        return deviceRepository.findOne(deviceEvent.getDeviceId()).getRoomNumber();
    }
}
