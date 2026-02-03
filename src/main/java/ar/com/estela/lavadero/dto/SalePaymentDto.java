package ar.com.estela.lavadero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalePaymentDto {
	
	private Long code;
	
	private Integer amount;
	
	private Integer reserve;

}
