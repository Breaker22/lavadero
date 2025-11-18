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
@Table(name = "laundry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laundry implements Serializable {

	private static final long serialVersionUID = 3508670304443479795L;
	
	@Id
	private Integer id;
	
	private String description;
	
	private Integer price;

}
