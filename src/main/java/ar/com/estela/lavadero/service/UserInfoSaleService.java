package ar.com.estela.lavadero.service;

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
	public UserSaleResponse getUserSalesByPhone(String phone) {
		UserInfoSale userSale = userInfoRepo.findById(phone).orElse(null);

		if (userSale == null) {
			return new UserSaleResponse();
		}

		List<LaundryDto> listLaundry = laundryRepo.findAllById(userSale.getListLaundry()).stream()
				.map(l -> new LaundryDto(l.getId(), l.getDescription(), l.getPrice())).toList();

		return UserSaleResponse.builder().phone(phone).name(userSale.getName()).listLaundry(listLaundry).build();
	}

	@Override
	public void saveUserSale(GenerateReceiptDto receiptDto) {
		List<Long> listCodes = receiptDto.getData().stream().map(GenerateReceiptData::getCode).toList();

		userInfoRepo.save(new UserInfoSale(receiptDto.getPhone(), receiptDto.getName(), listCodes));
	}

}
