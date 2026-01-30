package ar.com.estela.lavadero.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_info_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSale implements Serializable {
	
	private static final long serialVersionUID = 5235350295815858278L;

	@Id
	private String phoneDate;
	
	private String phone;
	
	private LocalDate date;
	
	private String name;
	
	private String address;
	
	private List<Long> listLaundry;

}
