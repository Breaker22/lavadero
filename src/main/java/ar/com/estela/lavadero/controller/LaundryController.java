package ar.com.estela.lavadero.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.dto.LaundryDto;
import ar.com.estela.lavadero.dto.LaundrySaleDto;
import ar.com.estela.lavadero.interfaces.GenerateReceiptInterface;
import ar.com.estela.lavadero.interfaces.LaundryInterface;
import ar.com.estela.lavadero.interfaces.UserInfoSaleInterface;
import ar.com.estela.lavadero.response.LaundrySaleResponse;
import ar.com.estela.lavadero.response.UserSaleResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/laundry", consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class LaundryController {

	private final GenerateReceiptInterface generateReceiptInterface;

	private final LaundryInterface laundryInterface;
	
	private final UserInfoSaleInterface userInfoSaleInterface;

	@PostMapping("/print")
	public ResponseEntity<byte[]> printReceipt(@RequestBody GenerateReceiptDto receiptDto) {
		userInfoSaleInterface.saveUserSale(receiptDto);
		return generateReceiptInterface.printReceipt(receiptDto);
	}

	@PostMapping
	public ResponseEntity<String> createLaundry(@RequestBody LaundryDto laundryDto) {
		laundryInterface.createLaundry(laundryDto);
		return ResponseEntity.ok().body("ok");
	}

	@GetMapping
	public ResponseEntity<List<LaundryDto>> getAllLaundry() {
		return ResponseEntity.ok().body(laundryInterface.getAllLaundry());
	}

	@PostMapping("/sale")
	public ResponseEntity<String> saveSale(@RequestBody List<LaundrySaleDto> laundrySaleDto) {
		laundryInterface.saveSale(laundrySaleDto);
		return ResponseEntity.ok().body("ok");
	}

	@GetMapping("/sale")
	public ResponseEntity<LaundrySaleResponse> getAllSales(@RequestParam String dateFrom, @RequestParam String dateTo) {
		return ResponseEntity.ok().body(laundryInterface.getAllSales(dateFrom, dateTo));
	}

	@DeleteMapping(value = "/sale", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> deleteSale(@RequestParam Long id) {
		laundryInterface.deleteSale(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/search-sale")
	public ResponseEntity<UserSaleResponse> getUserSalesByPhone(@RequestParam String phone, @RequestParam String date) {
		return ResponseEntity.ok().body(userInfoSaleInterface.getUserSalesByPhone(phone, date));
	}

}
