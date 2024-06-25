package com.example.junitmokito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SomeBusinessImplTest {
    @Test
    public void testSomeBusiness() {
        DataService dataServiceMock = mock(DataService.class);
        when(dataServiceMock.retrieveAllData()).thenReturn(new int[]{3,5,10});
        SomeBusinessImpl someBusinessImpl = new SomeBusinessImpl(dataServiceMock);
        assertEquals(10, someBusinessImpl.findTheGreatestFromAllData());
    }
}
