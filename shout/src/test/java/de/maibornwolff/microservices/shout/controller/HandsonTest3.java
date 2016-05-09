package de.maibornwolff.microservices.shout.controller;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.shout.service.ShoutService;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest3 {
    @Mock
    private ShoutService shoutService;

    @InjectMocks
    private ShoutController underTest;

    @Test
    public void testGetRoomCounts() throws Exception {
        //Given
        Mockito.doReturn(Arrays.asList("eins", "zwei")).when(shoutService).getPendingTextToSpeechItems();

        //When
        List<String> textToSpeechItems = underTest.getRoomCounts();

        //Then
        Mockito.verify(shoutService).getPendingTextToSpeechItems();
        Assert.assertNotNull(textToSpeechItems);
        Assert.assertTrue(textToSpeechItems.contains("eins"));
        Assert.assertTrue(textToSpeechItems.contains("zwei"));
    }
}