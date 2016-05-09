package de.maibornwolff.microservices.device;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest2 {

    @Mock
    private RabbitMQAdapter rabbitMQAdapter;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService underTest;

    ArgumentCaptor<DeviceEvent> deviceEventArgumentCaptor = ArgumentCaptor.forClass(DeviceEvent.class);

    @Test
    public void testProcessEvent() throws Exception {
        //Given
        String deviceId = "123";
        String badgeNumber = "345";
        String roomNumber = "383";
        DeviceEvent deviceEvent = new DeviceEvent(deviceId, badgeNumber);

        Mockito.doReturn(new Device(deviceId, roomNumber)).when(deviceRepository).findOne(deviceEvent.getDeviceId());

        //When
        underTest.processEvent(deviceEvent);

        //Then
        Mockito.verify(deviceRepository).findOne(deviceEvent.getDeviceId());
        Mockito.verify(rabbitMQAdapter).sendDeviceEvent(deviceEventArgumentCaptor.capture());
        Assert.assertEquals(roomNumber, deviceEventArgumentCaptor.getValue().getRoomNumber());
        Assert.assertEquals(deviceId, deviceEventArgumentCaptor.getValue().getDeviceId());
        Assert.assertEquals(badgeNumber, deviceEventArgumentCaptor.getValue().getBadgeNumber());

    }

}