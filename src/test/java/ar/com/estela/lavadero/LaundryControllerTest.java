package ar.com.estela.lavadero;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import ar.com.estela.lavadero.dto.GenerateReceiptData;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.dto.LaundryDto;
import ar.com.estela.lavadero.entity.Laundry;
import ar.com.estela.lavadero.repository.LaundryRepository;
import ar.com.estela.lavadero.service.GenerateReceiptService;
import ar.com.estela.lavadero.service.LaundryService;

@ExtendWith(MockitoExtension.class)
class LaundryControllerTest {

	@InjectMocks
	private LaundryService laundryService;

	@InjectMocks
	private GenerateReceiptService generateReceiptService;

	@Mock
	private LaundryRepository laundryRepo;
	
	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(generateReceiptService, "paperWidth", 76);
		ReflectionTestUtils.setField(generateReceiptService, "paperHeight", 210);
	}

	@Test
	void testCreateLaundryOk() {
		laundryService.createLaundry(new LaundryDto(1, "test", 10));

		Assertions.assertDoesNotThrow(() -> Exception.class);
	}

	@Test
	void testGetAllLaundryOk() {
		Mockito.when(laundryRepo.findAll()).thenReturn(Arrays.asList(new Laundry(1, "test", 10)));

		Assertions.assertNotNull(laundryService.getAllLaundry());
	}

	@Test
	void testPrintLaundry() {
		GenerateReceiptDto generateReceipt = new GenerateReceiptDto();

		generateReceipt.setData(Arrays.asList(new GenerateReceiptData(2, "test", 100)));
		generateReceipt.setName("test");
		generateReceipt.setPhone("122333");

		ResponseEntity<byte[]> response = generateReceiptService.printReceipt(generateReceipt);

		Assertions.assertNotNull(response.getBody());
	}
}