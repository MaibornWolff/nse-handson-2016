package de.maibornwolff.microservices.room.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import de.maibornwolff.microservices.room.event.RoomEvent;
import de.maibornwolff.microservices.room.event.RoomEventType;
import de.maibornwolff.microservices.room.model.Room;
import de.maibornwolff.microservices.room.model.RoomAllocation;
import de.maibornwolff.microservices.room.repository.RoomAllocationRepository;
import de.maibornwolff.microservices.room.repository.RoomRepository;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest3 {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomAllocationRepository roomAllocationRepository;

    @Mock
    private Logger LOGGER;

    @InjectMocks
    private RoomService underTest;

    private ArgumentCaptor<RoomEvent> roomEventArgumentCaptor = ArgumentCaptor.forClass(RoomEvent.class);
    private ArgumentCaptor<RoomAllocation> roomAllocationArgumentCaptor = ArgumentCaptor.forClass(RoomAllocation.class);


    @Test
    public void testSendEnterEvent() {
        //Given
        String badgeNr = "badge123";
        String roomNr = "room123";
        Mockito.doReturn(new Room(roomNr, "anyName", "anyFloor", "anyLocation")).when(roomRepository).findOne(roomNr);

        //When
        underTest.sendEnterEvent(badgeNr, roomNr);

        //Then
        Mockito.verify(roomAllocationRepository).save(roomAllocationArgumentCaptor.capture());
        Assert.assertEquals(badgeNr, roomAllocationArgumentCaptor.getValue().getBadgeNumber());
        Assert.assertEquals(roomNr, roomAllocationArgumentCaptor.getValue().getCurrentRoomNumber());

        Mockito.verify(rabbitTemplate).convertAndSend(roomEventArgumentCaptor.capture());
        Assert.assertEquals(roomNr, roomEventArgumentCaptor.getValue().getRoom().getRoomNumber());
        Assert.assertEquals(badgeNr, roomEventArgumentCaptor.getValue().getBadgeNumber());
        Assert.assertEquals(RoomEventType.ENTER, roomEventArgumentCaptor.getValue().getType());

    }
}