package de.maibornwolff.microservices.badgereader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class BadgeReaderService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BadgeReaderService.class);

    private static final String SHUTDOWN_COMMAND = "shutMeDown";

    @Value("${deviceId}")
    private String deviceId;

    @Autowired
    private DeviceServiceAdapter deviceServiceAdapter;


    @Override
    public void run(String... args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = in.readLine()) != null && !s.equalsIgnoreCase(SHUTDOWN_COMMAND)) {
            String badgeNumber = getValidBadgeNumber(s);
            if (badgeNumber != null) {
                LOGGER.info("BadgeNumber recognized: " + badgeNumber);
                sendDeviceEvent(badgeNumber, deviceId);
            }

        }
        System.exit(0);
    }

    private void sendDeviceEvent(String badgeNumber, String deviceId) {
        try {
            deviceServiceAdapter.sendDeviceEvent(badgeNumber, deviceId);
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    private String getValidBadgeNumber(String input) {
        input = StringUtils.right(input.trim(), 10);
        return input.matches("[0-9]{10}") ? input : null;
    }
}