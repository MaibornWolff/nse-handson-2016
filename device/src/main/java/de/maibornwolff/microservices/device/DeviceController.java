package de.maibornwolff.microservices.device;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */

@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(
            value = "/event",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void putEvent(@RequestBody DeviceEvent deviceEvent) {
        LOGGER.info("Incoming Event from Badge-Reader (via REST): " + deviceEvent);
        //Stufe 1 hier.
        /*
        HANDSON - 1. Runde
        - Event an die Methode an deviceService.processEvent(...) weiterleiten, um den Event zu behandeln
         */
        //
        deviceService.processEvent(deviceEvent);
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
