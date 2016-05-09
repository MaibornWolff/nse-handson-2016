package de.maibornwolff.microservices.dashboard.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.microservices.dashboard.model.RoomCount;
import de.maibornwolff.microservices.dashboard.service.DashboardService;

/**
 * @author Bartosz Boron, MaibornWolff GmbH
 */
@RestController
@RequestMapping(value = "/roomCount")
public class RoomCountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomCountController.class);

    @Autowired
    private DashboardService dashboardService;


    /**
     * REST-Endpoint that is pulled repeatedly by frontend to update it's view.
     *
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomCount> getRoomCounts() {
        /*
        HANDSON - 3. Runde
        - RoomCount-Liste von this.dashboardService ermitteln
            - dashboardService.getRoomCount()
        - Liste zurückgeben
         */
        //
        return dashboardService.getRoomCounts();
        //
    }
}
