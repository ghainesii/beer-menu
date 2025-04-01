package net.ghaines.beer.menu.ontap;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBeer() {
		return beer;
	}

	public void setBeer(String beer) {
		this.beer = beer;
	}

	public Double getAbv() {
		return abv;
	}

	public void setAbv(Double abv) {
		this.abv = abv;
	}

	public String getBrewer() {
		return brewer;
	}

	public void setBrewer(String brewer) {
		this.brewer = brewer;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public LocalDate getStartDa() {
		return startDa;
	}

	public void setStartDa(LocalDate startDa) {
		this.startDa = startDa;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public String getLogoBase64() {
		return logoBase64;
	}

	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}

}
