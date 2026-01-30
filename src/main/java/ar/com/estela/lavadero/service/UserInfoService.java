package ar.com.estela.lavadero.service;

import org.springframework.stereotype.Service;

import ar.com.estela.lavadero.dto.UserInfoDto;
import ar.com.estela.lavadero.entity.UserInfo;
import ar.com.estela.lavadero.interfaces.UserInfoInterface;
import ar.com.estela.lavadero.repository.UserInfoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserInfoService implements UserInfoInterface {

	private final UserInfoRepository userInfoRepo;

	@Override
	public void createUser(UserInfoDto userInfoDto) {
		UserInfo userInfo = new UserInfo();

		userInfo.setPhone(userInfoDto.getPhone());
		userInfo.setAddress(userInfoDto.getAddress());
		userInfo.setName(userInfoDto.getName());

		userInfoRepo.save(userInfo);
	}

	@Override
	public UserInfoDto getUserByPhone(String phone) {
		UserInfo userInfo = userInfoRepo.findById(phone).orElse(null);

		return UserInfoDto.builder().phone(phone).name(userInfo.getName()).address(userInfo.getAddress()).build();
	}

}
