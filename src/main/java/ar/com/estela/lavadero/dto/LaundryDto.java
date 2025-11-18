package ar.com.estela.lavadero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LaundryDto {
	
	private Integer code;
	
	private String description;
	
	private Integer price;

}
