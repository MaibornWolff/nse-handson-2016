package de.maibornwolff.microservices.shout.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.shout.adapter.AccountServiceAdapter;
import de.maibornwolff.microservices.shout.event.RoomEvent;
import de.maibornwolff.microservices.shout.event.RoomEventType;
import de.maibornwolff.microservices.shout.model.Account;
import de.maibornwolff.microservices.shout.model.Room;
import de.maibornwolff.microservices.shout.model.RoomAccount;
import de.maibornwolff.microservices.shout.repository.RoomAccountRepository;
import static org.mockito.Matchers.any;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest1 {

    @Mock
    private AccountServiceAdapter accountServiceAdapter;

    @Mock
    private RoomAccountRepository roomAccountRepository;

    @InjectMocks
    private ShoutService underTest;

    @Test
    public void testOnRoomEvent_LeaveEvent() throws Exception {
        //When
        underTest.onRoomEvent(new RoomEvent(new Room("1", "name", "floor", "location"), "badge123", RoomEventType.LEAVE));

        //Then
        Mockito.verifyZeroInteractions(accountServiceAdapter);
        Mockito.verifyZeroInteractions(roomAccountRepository);
    }

    @Test
    public void testOnRoomEvent_Enter() {
        //Given
        String roomNr = "room123";
        String badgeNr = "badge123";
        Room room = new Room(roomNr, "someName", "anyfloor", "somwhere over the rainbow");
        RoomEvent roomEvent = new RoomEvent(room,  badgeNr, RoomEventType.ENTER);
        Account account = new Account(badgeNr, "FirstName", "LastName", "CoolCompany", "cool@me.com");

        Mockito.doReturn(account).when(accountServiceAdapter).getAccountForBadge(badgeNr);

        //When
        underTest.onRoomEvent(roomEvent);

        //Then
        Mockito.verify(accountServiceAdapter).getAccountForBadge(badgeNr);
        Mockito.verify(roomAccountRepository).save(any(RoomAccount.class));
    }
}