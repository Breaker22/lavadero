package ar.com.estela.lavadero.interfaces;

import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.response.SaleBillingResponse;

public interface SaleBillingInterface {
	
	SaleBillingResponse getSalesByDate(String date);
	
	void saveSaleBilling(GenerateReceiptDto receiptDto);

}
