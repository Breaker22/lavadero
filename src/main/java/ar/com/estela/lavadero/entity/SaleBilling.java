package ar.com.estela.lavadero.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sale_billing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleBilling implements Serializable {
	
	private static final long serialVersionUID = 5235350295815858278L;

	@Id
	private LocalDate date;
	
	private Integer orders;
	
	private Integer items;

}
