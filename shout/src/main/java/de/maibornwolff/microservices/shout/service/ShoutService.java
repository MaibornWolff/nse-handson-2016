package de.maibornwolff.microservices.shout.service;

import com.google.common.annotations.VisibleForTesting;
import de.maibornwolff.microservices.shout.adapter.AccountServiceAdapter;
import de.maibornwolff.microservices.shout.event.RoomEvent;
import de.maibornwolff.microservices.shout.model.Account;
import de.maibornwolff.microservices.shout.model.RoomAccount;
import de.maibornwolff.microservices.shout.repository.RoomAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Jochem, MaibornWolff GmbH on 14.04.16.
 */
@Component
public class ShoutService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoutService.class);

    @Autowired
    private AccountServiceAdapter accountServiceAdapter;

    @Autowired
    private RoomAccountRepository roomAccountRepository;


    /**
     * Wird aufgerufen wenn neue RaumEreignisse in der Queue vorhanden sind
     */
    public void onRoomEvent(RoomEvent roomEvent) {
        LOGGER.info("Received RoomEvent: " + roomEvent);
        /*
        HANDSON - 1. Runde
        - Verarbeiten, nur wenn es sich um einen ENTER Event handelt ( if RoomEventType.ENTER )
            - Account zur BadgeNummer (roomEvent.getBadgeNumber()) von AccountService ( this.accountServiceAdapter ) abrufen
            - Account und Raum zum Sprechen vorbereiten
            - prepareTextToSpeech(account, roomEvent) aufrufen
        */
        //

        //
    }

    /**
     * Raum/Account Informationen speichern in der Datenbank
     */
    private void prepareTextToSpeech(Account account, RoomEvent roomEvent) {
        if (account == null) {
            //Save "Unknown"
            RoomAccount roomAccount = roomAccountRepository.save(
                    new RoomAccount("UNKNOWN" + roomEvent.getBadgeNumber(), "Unbekannte", "Person", roomEvent.getRoom().getName())
            );
            LOGGER.info("Saved TextToSpeech: " + roomAccount);
        } else {
            //Save user
            RoomAccount roomAccount = roomAccountRepository.save(
                    new RoomAccount(account.getBadgeNumber(), account.getFirstName(), account.getLastName(), roomEvent.getRoom().getName())
            );
            LOGGER.info("Saved TextToSpeech: " + roomAccount);
        }
    }


    /**
     * Liste der Texte für die Sprachausgabe im Frontend ermitteln
     */
    public List<String> getPendingTextToSpeechItems() {
        List<RoomAccount> roomAccounts = roomAccountRepository.findAll();
        roomAccountRepository.deleteAll();
        return getText2SpeechItems(roomAccounts);
    }


    private List<String> getText2SpeechItems(List<RoomAccount> roomAccounts) {
        List<String> text2SpeechList = new ArrayList<>();
        for (RoomAccount roomAccount : roomAccounts) {
            text2SpeechList.add(buildtext2Speech(roomAccount));
        }
        return text2SpeechList;
    }


    @VisibleForTesting
    protected String buildtext2Speech(RoomAccount roomAccount) {
        /*
        HANDSON - 2. Runde
        - Zu sprechende Textzeile erstellen ("<Vorname> <Nachname> hat den Raum <Raumname> betreten.")
        - Erzeugten String an den Aufrufer zurückgeben ( return ...; )
        - "return null;" entfernen!
         */
        //
        return null;
        //
    }
}
