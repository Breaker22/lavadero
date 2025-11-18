package ar.com.estela.lavadero.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.estela.lavadero.entity.LaundySale;

@Repository
public interface LaundrySaleRepository extends JpaRepository<LaundySale, Long> {
	
	List<LaundySale> findByDateBetween(LocalDate dateFrom, LocalDate dateTo);

}
