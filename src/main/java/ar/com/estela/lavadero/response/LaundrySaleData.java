package ar.com.estela.lavadero.response;

import ar.com.estela.lavadero.dto.LaundryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class LaundrySaleData extends LaundryDto {
	
	private Integer totalCash;

	private Integer totalPending;

	private Integer totalReserved;
	
	private Long idSale;

}
