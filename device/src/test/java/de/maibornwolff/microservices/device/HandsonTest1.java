package de.maibornwolff.microservices.device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.any;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest1 {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController underTest;

    @Test
    public void testPutEvent() throws Exception {
        //When
        underTest.putEvent(new DeviceEvent());

        //Then
        Mockito.verify(deviceService).processEvent(any(DeviceEvent.class));

    }

}