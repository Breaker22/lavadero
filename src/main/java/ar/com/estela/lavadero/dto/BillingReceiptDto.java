package ar.com.estela.lavadero.dto;

import ar.com.estela.lavadero.response.SaleBillingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BillingReceiptDto extends SaleBillingResponse {

	private Integer charge;
	
	private Integer spent;
	
	private Integer total;
	
	private Integer missing;
}
