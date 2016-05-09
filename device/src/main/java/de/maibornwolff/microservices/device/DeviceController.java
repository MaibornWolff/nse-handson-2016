package de.maibornwolff.microservices.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */

@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    /**
     * Schnittstelle die von Devices aufgerufen wird, sobald jemand die Karte dran hält
     */
    @RequestMapping(
            value = "/event",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void putEvent(@RequestBody DeviceEvent deviceEvent) {
        LOGGER.info("Incoming Event from Badge-Reader (via REST): " + deviceEvent);

        /*
        HANDSON - 1. Runde
        - Event an das Device-Service ( deviceService.processEvent(...); ) weiterleiten, um den Event zu behandeln
         */
        //

        //
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void saveDevice(@RequestBody Device device) {
        deviceService.assignDevice(device);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces =  MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }

}
