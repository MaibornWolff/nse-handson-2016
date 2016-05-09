package de.maibornwolff.microservices.device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import static org.mockito.Matchers.any;

/**
 * Created by philippla on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest3 {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQAdapter underTest;

    @Test
    public void testSendDeviceEvent() throws Exception {
        //When
        underTest.sendDeviceEvent(new DeviceEvent());

        //Then
        Mockito.verify(rabbitTemplate).convertAndSend(any(DeviceEvent.class));
    }

}