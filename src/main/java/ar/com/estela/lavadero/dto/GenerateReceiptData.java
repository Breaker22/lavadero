package ar.com.estela.lavadero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerateReceiptData {
	
	private Long code;
	
	private Integer quantity;
	
	private String description;
	
	private Integer price;

}
