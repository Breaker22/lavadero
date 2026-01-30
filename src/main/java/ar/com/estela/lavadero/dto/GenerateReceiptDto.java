package ar.com.estela.lavadero.dto;

import java.util.List;

import lombok.Data;

@Data
public class GenerateReceiptDto {
	
	private List<GenerateReceiptData> data;
	
	private String name;
	
	private String phone;
	
	private String payment;
	
	private String address;

}
