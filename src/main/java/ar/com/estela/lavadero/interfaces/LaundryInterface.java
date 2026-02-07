package ar.com.estela.lavadero.interfaces;

import java.util.List;

import ar.com.estela.lavadero.dto.LaundryDto;
import ar.com.estela.lavadero.dto.LaundrySaleDto;
import ar.com.estela.lavadero.response.LaundrySaleResponse;

public interface LaundryInterface {
	
	public void createLaundry(LaundryDto laundryDto);
	
	public List<LaundryDto> getAllLaundry();
	
	public Long saveSale(LaundrySaleDto laundrySaleDto);
	
	public LaundrySaleResponse getAllSales(String dateFrom, String dateTo);
	
	public void deleteSale(Long id);

}
