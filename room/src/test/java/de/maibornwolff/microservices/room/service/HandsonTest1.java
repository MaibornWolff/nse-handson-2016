package de.maibornwolff.microservices.room.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import de.maibornwolff.microservices.room.event.DeviceEvent;
import de.maibornwolff.microservices.room.model.RoomAllocation;
import de.maibornwolff.microservices.room.repository.RoomAllocationRepository;
import de.maibornwolff.microservices.room.repository.RoomRepository;
import static org.mockito.Mockito.*;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest1 {

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

    private static final DeviceEvent DEVICE_EVENT = new DeviceEvent("device123", "badge123", "room123");

    @Test
    public void testOnDeviceEvent_Leave_Only() {

        //Given
        RoomAllocation currentRoomAllocation = new RoomAllocation(DEVICE_EVENT.getBadgeNumber(), DEVICE_EVENT.getRoomNumber());
        Mockito.doReturn(currentRoomAllocation).when(roomAllocationRepository).findOne(DEVICE_EVENT.getBadgeNumber());

        //When
        underTest.onDeviceEvent(DEVICE_EVENT);

        //Then
        Mockito.verify(roomAllocationRepository, times(2)).findOne(DEVICE_EVENT.getBadgeNumber()); //1x on isUserInRoom, 1x on sendLeaveEvent
    }

    @Test
    public void testOnDeviceEvent_Enter() {

        //Given
        RoomAllocation currentRoomAllocation = new RoomAllocation(DEVICE_EVENT.getBadgeNumber(), "otherRoom");
        Mockito.doReturn(currentRoomAllocation).when(roomAllocationRepository).findOne(DEVICE_EVENT.getBadgeNumber());

        //When
        underTest.onDeviceEvent(DEVICE_EVENT);

        //Then
        Mockito.verify(roomAllocationRepository, times(2)).findOne(DEVICE_EVENT.getBadgeNumber()); //1x on isUserInRoom, 1x on sendLeaveEvent
        Mockito.verify(LOGGER).trace("called sendEnterEvent");
    }

}