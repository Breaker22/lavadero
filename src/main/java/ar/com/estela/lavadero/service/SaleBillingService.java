package ar.com.estela.lavadero.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import ar.com.estela.lavadero.dto.GenerateReceiptData;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.entity.SaleBilling;
import ar.com.estela.lavadero.interfaces.SaleBillingInterface;
import ar.com.estela.lavadero.repository.SaleBillingRepository;
import ar.com.estela.lavadero.response.SaleBillingResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SaleBillingService implements SaleBillingInterface {

	private final SaleBillingRepository saleBillingRepo;

	@Override
	public SaleBillingResponse getSalesByDate(String date) {
		SaleBilling saleBilling = saleBillingRepo.findById(LocalDate.parse(date)).orElse(null);

		if (saleBilling == null) {
			return new SaleBillingResponse();
		}

		return SaleBillingResponse.builder().date(date).orders(saleBilling.getOrders()).items(saleBilling.getItems())
				.build();
	}

	@Override
	public void saveSaleBilling(GenerateReceiptDto receiptDto) {
		ZoneId argentinaZone = ZoneId.of("America/Argentina/Buenos_Aires");
		LocalDate today = LocalDate.now(argentinaZone);

		SaleBilling saleBilling = saleBillingRepo.findById(today).orElse(null);

		if (saleBilling == null) {
			saleBilling = new SaleBilling();
			saleBilling.setDate(today);
			saleBilling.setItems(0);
			saleBilling.setOrders(0);
		}

		Integer items = receiptDto.getData().stream().mapToInt(GenerateReceiptData::getQuantity).sum();

		saleBillingRepo.save(new SaleBilling(today, Integer.sum(saleBilling.getOrders(), 1),
				Integer.sum(saleBilling.getItems(), items)));
	}

}
