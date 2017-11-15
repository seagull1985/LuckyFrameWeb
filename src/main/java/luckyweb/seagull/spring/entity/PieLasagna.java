package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="pielasagna")
public class PieLasagna implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private double value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
