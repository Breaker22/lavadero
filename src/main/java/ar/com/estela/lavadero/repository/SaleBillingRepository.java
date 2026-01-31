package ar.com.estela.lavadero.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.estela.lavadero.entity.SaleBilling;

@Repository
public interface SaleBillingRepository extends JpaRepository<SaleBilling, LocalDate> {

}
