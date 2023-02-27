package net.ghaines.beer.menu.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class OnTap {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	@Column
	private String beer;

	@Column
	private Double abv;

	@Column
	private String brewer;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private String style;

	@Column
	private byte[] logo;

	@Column
	private LocalDate startDa;

	@Transient
	private boolean isNew;

	@Transient
	private String logoBase64;

}
