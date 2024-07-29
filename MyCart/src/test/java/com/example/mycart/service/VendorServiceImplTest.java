package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Vendor;
import com.example.mycart.payloads.VendorDTO;
import com.example.mycart.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    @Mock
    private VendorRepository repository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private VendorServiceImpl vendorService;

    private Vendor vendor;

    private VendorDTO vendorDTO;

    @BeforeEach
    void setUp() {
        vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Test Vendor");

        vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("Test Vendor");
    }

    @Test
    void testCreate() {
        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        Vendor result = vendorService.create(vendor);

        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getName(), result.getName());
        verify(repository, times(1)).save(any(Vendor.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));

        Vendor result = vendorService.findById(1L);

        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getName(), result.getName());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vendorService.findById(1L));
        verify(repository, times(1)).findById(anyLong());
    }

//    @Test
//    void testFindAll() {
//        List<Vendor> vendors = Collections.singletonList(vendor);
//        Page<Vendor> page = new PageImpl<>(vendors);
//        when(repository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        Page<Vendor> result = vendorService.findAll(0);
//
//        assertNotNull(result);
//        assertEquals(1, result.getTotalElements());
//        assertEquals(vendor.getId(), result.getContent().get(0).getId());
//        verify(repository, times(1)).findAll(any(PageRequest.class));
//    }

    @Test
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        Vendor result = vendorService.update(1L, vendorDTO);

        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getName(), result.getName());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Vendor.class));
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));

        Vendor result = vendorService.delete(1L);

        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getName(), result.getName());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Vendor.class));
    }

    @Test
    void testGenerateAnalysis() {
        List<Object[]> analysisData = Arrays.asList(
                new Object[]{"Product1", 10, "User1", "Address1", 5, 100.0, "Completed"},
                new Object[]{"Product2", 20, "User2", "Address2", 10, 200.0, "Pending"}
        );
        when(repository.findAnalysis(anyLong())).thenReturn(analysisData);

        byte[] result = vendorService.generateAnalysis(1L);

        assertNotNull(result);
        assertTrue(result.length > 0);
        verify(repository, times(1)).findAnalysis(anyLong());
    }
}
