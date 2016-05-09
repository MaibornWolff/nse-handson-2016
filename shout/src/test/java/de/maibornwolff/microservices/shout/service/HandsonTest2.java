package de.maibornwolff.microservices.shout.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.shout.adapter.AccountServiceAdapter;
import de.maibornwolff.microservices.shout.model.RoomAccount;
import de.maibornwolff.microservices.shout.repository.RoomAccountRepository;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest2 {

    @Mock
    private AccountServiceAdapter accountServiceAdapter;

    @Mock
    private RoomAccountRepository roomAccountRepository;

    @InjectMocks
    private ShoutService underTest;

    @Test
    public void testBuildTextToSpeech() {
        //Given
        RoomAccount roomAccount = new RoomAccount("SomeBadge", "someFirstName", "someLastName", "someRoomName");

        //When
        String s = underTest.buildtext2Speech(roomAccount);

        //Then
        Assert.assertNotNull(s);
        Assert.assertTrue("Does not contain first name", s.contains(roomAccount.getFirstName()));
        Assert.assertTrue("Does not contain last name", s.contains(roomAccount.getLastName()));
        Assert.assertTrue("Does not contain room name", s.contains(roomAccount.getRoomName()));
    }
}