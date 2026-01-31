package ar.com.estela.lavadero.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.estela.lavadero.dto.GenerateReceiptData;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.dto.LaundryDto;
import ar.com.estela.lavadero.entity.UserInfoSale;
import ar.com.estela.lavadero.interfaces.UserInfoSaleInterface;
import ar.com.estela.lavadero.repository.LaundryRepository;
import ar.com.estela.lavadero.repository.UserInfoSaleRepository;
import ar.com.estela.lavadero.response.UserSaleResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserInfoSaleService implements UserInfoSaleInterface {

	private final UserInfoSaleRepository userInfoRepo;
	private final LaundryRepository laundryRepo;

	@Override
	public UserSaleResponse getUserSalesByPhone(String phone, String date) {
		UserInfoSale userSale = userInfoRepo
				.findById(new StringBuilder(phone).append("*").append(LocalDate.parse(date)).toString()).orElse(null);

		if (userSale == null) {
			return new UserSaleResponse();
		}

		List<LaundryDto> listLaundry = laundryRepo.findAllById(userSale.getListLaundry()).stream()
				.map(l -> new LaundryDto(l.getId(), l.getDescription(), l.getPrice())).toList();

		return UserSaleResponse.builder().phone(phone).name(userSale.getName()).date(userSale.getDate().toString())
				.listLaundry(listLaundry).build();
	}

	@Override
	public void saveUserSale(GenerateReceiptDto receiptDto) {
		List<Long> listCodes = receiptDto.getData().stream().map(GenerateReceiptData::getCode).toList();
		ZoneId argentinaZone = ZoneId.of("America/Argentina/Buenos_Aires");
		LocalDate today = LocalDate.now(argentinaZone);

		userInfoRepo.save(new UserInfoSale(
				new StringBuilder(receiptDto.getPhone()).append("*").append(today.toString()).toString(),
				receiptDto.getPhone(), today, receiptDto.getName(), receiptDto.getAddress() , listCodes));
	}

}
