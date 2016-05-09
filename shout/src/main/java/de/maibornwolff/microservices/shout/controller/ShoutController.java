package de.maibornwolff.microservices.shout.controller;


/**
 * Created by Andreas Jochem, MaibornWolff GmbH on 25.04.16.
 */

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.maibornwolff.microservices.shout.service.ShoutService;

@RestController
@RequestMapping(value = "/shout")
public class ShoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoutController.class);

    @Autowired
    private ShoutService shoutService;


    /**
     * REST-Endpoint that is pulled repeatedly from frontend. Speech rendering happens at javascript frontend.
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
        - Offene Text2Speech Items aus this.shoutService auslesen
            - shoutService.getPendingTextToSpeechItems()
        - Text2SpeechList an Aufrufer zurückgeben.
            - return ...
         */
        //
        List<String> text2SpeechList = shoutService.getPendingTextToSpeechItems();
        if (text2SpeechList.size() != 0) {
            LOGGER.info("Sending " + text2SpeechList.size() + " speeches to frontend.");
        }
        return text2SpeechList;
        //
    }
}

