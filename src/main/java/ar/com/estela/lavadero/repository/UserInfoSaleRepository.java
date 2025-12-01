package ar.com.estela.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.estela.lavadero.entity.UserInfoSale;

@Repository
public interface UserInfoSaleRepository extends JpaRepository<UserInfoSale, String> {

}
