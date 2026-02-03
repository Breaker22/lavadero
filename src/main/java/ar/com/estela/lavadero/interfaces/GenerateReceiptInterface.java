package ar.com.estela.lavadero.interfaces;

import org.springframework.http.ResponseEntity;

import ar.com.estela.lavadero.dto.BillingReceiptDto;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;

public interface GenerateReceiptInterface {
	
	public ResponseEntity<byte[]> printReceipt(Long randomNum, GenerateReceiptDto receiptDto);
	
	public ResponseEntity<byte[]> printBilling(BillingReceiptDto request);

}
