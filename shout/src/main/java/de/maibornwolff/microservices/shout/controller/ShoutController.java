package de.maibornwolff.microservices.shout.controller;


/**
 * Created by Andreas Jochem, MaibornWolff GmbH on 25.04.16.
 */

import de.maibornwolff.microservices.shout.service.ShoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/shout")
public class ShoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoutController.class);

    @Autowired
    private ShoutService shoutService;


    /**
     * REST-Schnittstelle die wiederholt vom Frontend aufgerufen wird um die angezeigten Daten zu aktualisieren
     *
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> getRoomCounts() {
        /*
        HANDSON - 3. Runde
        - Die zu sprechenden Text-Zeilen aus dem Shout-Service ( this.shoutService.getPendingTextToSpeechItems(); ) auslesen
        - Text2SpeechList an Aufrufer zurückgeben ( return ...; )
        - "return null;" entfernen
         */
        //
        return null;
        //
    }
}

