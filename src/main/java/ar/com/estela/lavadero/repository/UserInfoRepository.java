package ar.com.estela.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.estela.lavadero.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

}
