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
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 2491268884128109539L;

	@Id
	private String phone;
	
	private String name;
	
	private String address;
}
