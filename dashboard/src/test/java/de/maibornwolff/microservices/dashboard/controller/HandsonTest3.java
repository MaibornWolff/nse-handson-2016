package de.maibornwolff.microservices.dashboard.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import de.maibornwolff.microservices.dashboard.service.DashboardService;

/**
 * Created by philippla on 07/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandsonTest3 {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private RoomCountController underTest;


    @Test
    public void testGetRoomCounts() {
        //When
        underTest.getRoomCounts();

        //Then
        Mockito.verify(dashboardService).getRoomCounts();
    }
}