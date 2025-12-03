package ar.com.estela.lavadero.interfaces;

import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.response.UserSaleResponse;

public interface UserInfoSaleInterface {
	
	UserSaleResponse getUserSalesByPhone(String phone, String date);
	
	void saveUserSale(GenerateReceiptDto receiptDto);

}
