package de.maibornwolff.microservices.badgereader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import feign.Feign;
import feign.Param;
import feign.RequestLine;

/**
 * Created by Philipp Lamp, MaibornWolff GmbH
 */
@Component
public class ConsulAdapter {

    private static int instance = 0;

    @Value("${consulHost}")
    private String consulHost;

    public String getServiceUrl(String serviceName) {
        IConsulClient restClient = Feign.builder()
                .target(IConsulClient.class, "http://" + consulHost + ":8500");

        String serviceJson = restClient.getHealthyService(serviceName);
        return getUrlFromServiceJson(serviceJson);
    }


    private String getUrlFromServiceJson(String serviceJson) {
        JSONArray rootArray = new JSONArray(serviceJson);
        if (rootArray.length() == 0) {
            throw new IllegalStateException("No Service Online");
        }
        instance++;

        if (instance >= rootArray.length()) {
            instance = 0;
        }

        JSONObject serviceData = rootArray.getJSONObject(instance);

        JSONObject serviceObject = serviceData.getJSONObject("Service");
        String address = serviceObject.getString("Address");
        int port = serviceObject.getInt("Port");

        return "http://" + address + ":" + port;
    }


    public interface IConsulClient {
        @RequestLine("GET /v1/health/service/{service}?passing=1")
        String getHealthyService(@Param("service") String service);
    }
}
