package ar.com.estela.lavadero.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import ar.com.estela.lavadero.dto.GenerateReceiptData;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.dto.SalePaymentDto;
import ar.com.estela.lavadero.entity.SaleBilling;
import ar.com.estela.lavadero.entity.SalePayment;
import ar.com.estela.lavadero.interfaces.SaleBillingInterface;
import ar.com.estela.lavadero.repository.SaleBillingRepository;
import ar.com.estela.lavadero.repository.SalePaymentRepository;
import ar.com.estela.lavadero.response.SaleBillingResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SaleBillingService implements SaleBillingInterface {

	private final SaleBillingRepository saleBillingRepo;
	private final SalePaymentRepository salePaymentRepo;

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
	public void saveSaleBilling(Long randomNum, GenerateReceiptDto receiptDto) {
		ZoneId argentinaZone = ZoneId.of("America/Argentina/Buenos_Aires");
		LocalDate today = LocalDate.now(argentinaZone);

		SaleBilling saleBilling = saleBillingRepo.findById(today).orElse(null);
		SalePayment salePayment = new SalePayment();

		if (saleBilling == null) {
			saleBilling = new SaleBilling();
			saleBilling.setDate(today);
			saleBilling.setItems(0);
			saleBilling.setOrders(0);
		}

		salePayment.setCode(randomNum);
		salePayment.setPayment(receiptDto.getPayment());
		salePayment.setAmount(receiptDto.getData().stream().mapToInt(GenerateReceiptData::getPrice).sum());
		salePayment.setReserve(receiptDto.getReserve());

		Integer items = receiptDto.getData().stream().mapToInt(GenerateReceiptData::getQuantity).sum();

		saleBillingRepo.save(new SaleBilling(today, Integer.sum(saleBilling.getOrders(), 1),
				Integer.sum(saleBilling.getItems(), items)));

		salePaymentRepo.save(salePayment);
	}

	@Override
	public void saveSalePayment(SalePaymentDto salePaymentDto) {
		SalePayment salePayment = salePaymentRepo.findById(salePaymentDto.getCode()).orElse(null);

		if (salePayment != null) {
			Integer totalDiscount = salePayment.getReserve() + salePaymentDto.getReserve();

			if (totalDiscount <= salePayment.getAmount()) {
				salePayment.setReserve(totalDiscount);
				salePaymentRepo.save(salePayment);
			}
		}
	}

	@Override
	public SalePaymentDto getSalePayment(Long code) {
		SalePayment salePayment = salePaymentRepo.findById(code).orElse(null);
		
		if (salePayment == null) {
			return new SalePaymentDto();
		}

		return new SalePaymentDto(salePayment.getCode(), salePayment.getAmount(), salePayment.getReserve());
	}

}
