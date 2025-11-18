package ar.com.estela.lavadero.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "laundry_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaundySale {

	@Id
	@GeneratedValue
	private Long id;
	
	private Integer totalCash;
	
	private Integer totalPending;
	
	private Integer totalReserved;
	
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "laundry_id")
	private Laundry laundry;
}
