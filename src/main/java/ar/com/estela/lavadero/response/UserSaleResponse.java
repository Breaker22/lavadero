package ar.com.estela.lavadero.response;

import java.util.List;

import ar.com.estela.lavadero.dto.LaundryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaleResponse {
	
	private String phone;
	
	private String name;
	
	private String date;
	
	private List<LaundryDto> listLaundry;

}
