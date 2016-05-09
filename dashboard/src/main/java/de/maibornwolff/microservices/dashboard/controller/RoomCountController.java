package de.maibornwolff.microservices.dashboard.controller;

import de.maibornwolff.microservices.dashboard.model.RoomCount;
import de.maibornwolff.microservices.dashboard.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * REST-Schnittstelle die wiederholt vom Frontend aufgerufen wird um die angezeigten Daten zu aktualisieren
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomCount> getRoomCounts() {
        /*
        HANDSON - 3. Runde
        - Liste mit allen Raum-Zählern vom 'dashboardService' ermitteln
        - Liste zurückgeben
        - "return null;" entfernen!
         */
        //
        return null;
        //
    }
}
