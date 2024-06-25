package com.example.junitmokito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SomeBusinessImplMockAnnotationTest {

    @Mock
    private DataService dataServiceMock;

    @InjectMocks
    private SomeBusinessImpl someBusinessImpl;

    @Test
    public void testSomeBusiness() {
        when(dataServiceMock.retrieveAllData()).thenReturn(new int[]{3,5,10});
        assertEquals(10, someBusinessImpl.findTheGreatestFromAllData());
    }

    @Test
    public void testSomeBusiness2() {
        when(dataServiceMock.retrieveAllData()).thenReturn(new int[]{});
        assertEquals(Integer.MIN_VALUE, someBusinessImpl.findTheGreatestFromAllData());
    }

}
