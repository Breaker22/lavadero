package ar.com.estela.lavadero.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.estela.lavadero.dto.LaundryDto;
import ar.com.estela.lavadero.dto.LaundrySaleDto;
import ar.com.estela.lavadero.entity.Laundry;
import ar.com.estela.lavadero.entity.LaundySale;
import ar.com.estela.lavadero.interfaces.LaundryInterface;
import ar.com.estela.lavadero.repository.LaundryRepository;
import ar.com.estela.lavadero.repository.LaundrySaleRepository;
import ar.com.estela.lavadero.response.LaundrySaleData;
import ar.com.estela.lavadero.response.LaundrySaleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class LaundryService implements LaundryInterface {

	private final LaundryRepository laundryRepo;
	private final LaundrySaleRepository laundrySaleRepo;

	@Override
	public void createLaundry(LaundryDto laundryDto) {

		log.info(" Se crea el lavado --");

		Laundry laundry = new Laundry();
		laundry.setId(laundryDto.getCode());
		laundry.setDescription(laundryDto.getDescription());
		laundry.setPrice(laundryDto.getPrice());

		log.info(" Fin creacion de lavado --");

		laundryRepo.save(laundry);
	}

	@Override
	public List<LaundryDto> getAllLaundry() {
		List<Laundry> listLaundry = laundryRepo.findAll();
		ArrayList<LaundryDto> response = new ArrayList<>();

		for (Laundry laundry : listLaundry) {
			response.add(new LaundryDto(laundry.getId(), laundry.getDescription(), laundry.getPrice()));
		}

		return response;
	}

	@Override
	public void saveSale(List<LaundrySaleDto> laundrySaleDto) {
		for (LaundrySaleDto auxSale : laundrySaleDto) {
			LaundySale laundrySale = new LaundySale();
			Laundry laundry = laundryRepo.findById(auxSale.getLaundryId()).orElseThrow(() -> new RuntimeException());

			Integer total = laundry.getPrice() * auxSale.getQuantity();

			laundrySale.setLaundry(laundry);
			laundrySale.setDate(LocalDate.now());

			switch (auxSale.getPaymentCode()) {
			case 1:
				laundrySale.setTotalCash(total);
				laundrySale.setTotalReserved(0);
				laundrySale.setTotalPending(0);
				break;
			case 2:
				laundrySale.setTotalReserved(total);
				laundrySale.setTotalCash(0);
				laundrySale.setTotalPending(0);
				break;
			case 3:
				laundrySale.setTotalPending(total);
				laundrySale.setTotalCash(0);
				laundrySale.setTotalReserved(0);
				break;
			default:
				break;
			}

			laundrySaleRepo.save(laundrySale);
		}
	}

	@Override
	public LaundrySaleResponse getAllSales(String dateFrom, String dateTo) {
		LaundrySaleResponse saleResponse = new LaundrySaleResponse();

		List<LaundySale> listSales = laundrySaleRepo.findByDateBetween(LocalDate.parse(dateFrom),
				LocalDate.parse(dateTo));
		ArrayList<LaundrySaleData> listSaleData = new ArrayList<>();

		Integer totalCash = 0;
		Integer totalPending = 0;
		Integer totalReserved = 0;

		if (CollectionUtils.isEmpty(listSales)) {
			return null;
		}

		for (LaundySale laundrySale : listSales) {
			LaundrySaleData laundrySaleData = new LaundrySaleData();

			totalCash = totalCash + laundrySale.getTotalCash();
			totalPending = totalPending + laundrySale.getTotalPending();
			totalReserved = totalReserved + laundrySale.getTotalReserved();

			laundrySaleData.setCode(laundrySale.getLaundry().getId());
			laundrySaleData.setIdSale(laundrySale.getId());
			laundrySaleData.setDescription(laundrySale.getLaundry().getDescription());
			laundrySaleData.setPrice(laundrySale.getLaundry().getPrice());
			laundrySaleData.setTotalCash(laundrySale.getTotalCash());
			laundrySaleData.setTotalPending(laundrySale.getTotalPending());
			laundrySaleData.setTotalReserved(laundrySale.getTotalReserved());

			listSaleData.add(laundrySaleData);
		}

		saleResponse.setListSales(listSaleData);
		saleResponse.setTotalCash(totalCash);
		saleResponse.setTotalPending(totalPending);
		saleResponse.setTotalReserved(totalReserved);

		return saleResponse;
	}

	@Override
	public void deleteSale(Long id) {
		laundrySaleRepo.deleteById(id);
	}
}
