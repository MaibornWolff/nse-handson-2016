package de.maibornwolff.microservices.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class DeviceRepository {

    private HashMap<String, Device> devices;

    @PostConstruct
    public void initDb() {
        devices = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            devices.put("" + i, new Device("" + i, "" + i));
        }
    }

    public List<Device> findAll() {
        return new ArrayList<>(devices.values());
    }

    public Device findOne(String deviceId) {
        return devices.get(deviceId);
    }
}
