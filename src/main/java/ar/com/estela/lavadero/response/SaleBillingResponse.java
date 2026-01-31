package ar.com.estela.lavadero.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleBillingResponse {
	
	private String date;
	
	private Integer orders;
	
	private Integer items;

}
