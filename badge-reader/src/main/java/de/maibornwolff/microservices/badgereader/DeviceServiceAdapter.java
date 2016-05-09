package de.maibornwolff.microservices.badgereader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import feign.Feign;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class DeviceServiceAdapter {

    private static final String DEVICE_SERVICE_NAME = "device-8080";

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceAdapter.class);

    @Autowired
    private ConsulAdapter consulAdapter;

    public void sendDeviceEvent(String badgeNumber, String deviceId) {
        String deviceUrl = consulAdapter.getServiceUrl(DEVICE_SERVICE_NAME);

        DeviceServiceClient restClient = Feign.builder()
                .target(DeviceServiceClient.class, deviceUrl);

        LOGGER.info("Chosen DeviceService: " + deviceUrl + ". Sending DeviceEvent. BadgeNumber:" + badgeNumber + ", deviceId:" + deviceId);
        restClient.sendDeviceEvent(badgeNumber, deviceId);
    }
}
