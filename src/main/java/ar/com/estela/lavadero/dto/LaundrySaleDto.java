package ar.com.estela.lavadero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LaundrySaleDto {
	
	private Long laundryId;
	
	private Integer quantity;
	
	private Integer paymentCode;

}
