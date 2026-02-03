package ar.com.estela.lavadero.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sale_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalePayment implements Serializable {
	
	private static final long serialVersionUID = -6358369778142819282L;
	
	@Id
	private Long code;
	
	private String payment;
	
	private Integer amount;
	
	private Integer reserve;

}
