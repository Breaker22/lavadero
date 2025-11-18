package ar.com.estela.lavadero.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LaundrySaleResponse {
	
	private List<LaundrySaleData> listSales;
	
	private Integer totalCash;
	
	private Integer totalPending;
	
	private Integer totalReserved;


}
