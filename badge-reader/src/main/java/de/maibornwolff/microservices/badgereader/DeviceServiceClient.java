package de.maibornwolff.microservices.badgereader;

import org.springframework.http.MediaType;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
public interface DeviceServiceClient {

    @RequestLine("POST /device/event")
    @Headers("Content-Type: " + MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Body("%7B\"badgeNumber\": \"{badgeNumber}\", \"deviceId\": \"{deviceId}\"%7D")
    void sendDeviceEvent(@Param("badgeNumber") String badgeNumber, @Param("deviceId") String deviceId);
}
