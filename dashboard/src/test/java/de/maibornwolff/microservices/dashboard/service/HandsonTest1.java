package de.maibornwolff.microservices.dashboard.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.dashboard.event.RoomEvent;
import de.maibornwolff.microservices.dashboard.event.RoomEventType;
import de.maibornwolff.microservices.dashboard.model.Room;
import de.maibornwolff.microservices.dashboard.model.RoomCount;
import de.maibornwolff.microservices.dashboard.repository.RoomCountRepository;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest1 {

    @Mock
    private RoomCountRepository roomCountRepository;

    @InjectMocks
    private DashboardService underTest;


    /**
     * Test: If roomEvent is filled correctly,
     */
    @Test
    public void testOnRoomEvent() {
        //Given
        Room room = new Room("2", "SomeRoom", "second Floor", "somewhere");
        RoomEvent roomEvent = new RoomEvent(room, "12345", RoomEventType.ENTER);
        RoomCount roomCount = new RoomCount(room.getRoomNumber(), room.getName(), 0);
        Mockito.doReturn(null).when(roomCountRepository).findOne(room.getRoomNumber());
        Mockito.doReturn(roomCount).when(roomCountRepository).save(any(RoomCount.class));

        //When
        underTest.onRoomEvent(roomEvent);

        //Then
        Mockito.verify(roomCountRepository).findOne(room.getRoomNumber());
        Mockito.verify(roomCountRepository, atLeast(1)).save(any(RoomCount.class));
    }

    /**
     * Test: Don't call saveEvent() if roomEvent.getRoom() is null
     */
    @Test
    public void testOnRoomEvent_null() {
        //When
        underTest.onRoomEvent(new RoomEvent(null, "someNumber", RoomEventType.ENTER));
        underTest.onRoomEvent(new RoomEvent(new Room(), null, RoomEventType.ENTER));
        underTest.onRoomEvent(new RoomEvent(new Room(), "someNumber", null));

        //Then
        Mockito.verifyZeroInteractions(roomCountRepository);
    }
}