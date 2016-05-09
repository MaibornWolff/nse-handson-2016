package de.maibornwolff.microservices.dashboard.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.dashboard.event.RoomEvent;
import de.maibornwolff.microservices.dashboard.event.RoomEventType;
import de.maibornwolff.microservices.dashboard.model.Room;
import de.maibornwolff.microservices.dashboard.model.RoomCount;
import de.maibornwolff.microservices.dashboard.repository.RoomCountRepository;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest2 {

    private static final Room ROOM = new Room("3", "SomeRoooooom", "any Floor", "at the very beginning");
    private static final RoomEvent ENTER_EVENT = new RoomEvent(ROOM, "1234", RoomEventType.ENTER);
    private static final RoomEvent LEAVE_EVENT = new RoomEvent(ROOM, "1234", RoomEventType.LEAVE);


    @Mock
    private RoomCountRepository roomCountRepository;

    @InjectMocks
    private DashboardService underTest;

    ArgumentCaptor<RoomCount> roomCountArgumentCaptor = ArgumentCaptor.forClass(RoomCount.class);


    @Test
    public void testSaveRoomEvent_Enter() {
        //Given
        RoomCount currentRoomCount = new RoomCount(ROOM.getRoomNumber(), ROOM.getName(), 2);
        Mockito.doReturn(currentRoomCount).when(roomCountRepository).findOne(ROOM.getRoomNumber());

        //When
        underTest.onRoomEvent(ENTER_EVENT);

        //Then
        Mockito.verify(roomCountRepository).save(roomCountArgumentCaptor.capture());
        Assert.assertEquals(3, roomCountArgumentCaptor.getValue().getCount());
    }

    @Test
    public void testSaveRoomEvent_Leave() {
        //Given
        RoomCount currentRoomCount = new RoomCount(ROOM.getRoomNumber(), ROOM.getName(), 2);
        Mockito.doReturn(currentRoomCount).when(roomCountRepository).findOne(ROOM.getRoomNumber());

        //When
        underTest.onRoomEvent(LEAVE_EVENT);

        //Then
        Mockito.verify(roomCountRepository).save(roomCountArgumentCaptor.capture());
        Assert.assertEquals(1, roomCountArgumentCaptor.getValue().getCount());
    }





}