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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest2 {

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


    @Test
    public void testSendLeaveEvent_UserNotCurrentlyInARoom() {
        //Given
        String badgeNr = "badge123";
        Mockito.doReturn(null).when(roomAllocationRepository).findOne(badgeNr);

        //When
        underTest.sendLeaveEvent(badgeNr);

        //Then
        Mockito.verify(roomAllocationRepository).findOne(badgeNr);
        Mockito.verify(roomAllocationRepository, times(0)).delete(any(RoomAllocation.class));
    }

    @Test
    public void testSendLeaveEvent_UserCurrentlyInRoom() {
        //Given
        String badgeNr = "badge123";
        String roomNr = "room123";
        RoomAllocation currentAllocation = new RoomAllocation(badgeNr, roomNr);
        Mockito.doReturn(currentAllocation).when(roomAllocationRepository).findOne(badgeNr);
        Mockito.doReturn(new Room(roomNr, "SomeName", "SomeFloor", "SomeLocation")).when(roomRepository).findOne(roomNr);


        //When
        underTest.sendLeaveEvent(badgeNr);

        //Then
        Mockito.verify(roomAllocationRepository).findOne(badgeNr);
        Mockito.verify(roomAllocationRepository).delete(currentAllocation);
        Mockito.verify(rabbitTemplate).convertAndSend(roomEventArgumentCaptor.capture());
        Assert.assertEquals(badgeNr, roomEventArgumentCaptor.getValue().getBadgeNumber());
        Assert.assertEquals(RoomEventType.LEAVE, roomEventArgumentCaptor.getValue().getType());
        Assert.assertEquals(roomNr, roomEventArgumentCaptor.getValue().getRoom().getRoomNumber());

    }
}