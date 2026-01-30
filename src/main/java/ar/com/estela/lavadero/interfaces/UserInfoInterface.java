package ar.com.estela.lavadero.interfaces;

import ar.com.estela.lavadero.dto.UserInfoDto;

public interface UserInfoInterface {

	void createUser(UserInfoDto userInfoDto);
	
	UserInfoDto getUserByPhone(String phone);
}
