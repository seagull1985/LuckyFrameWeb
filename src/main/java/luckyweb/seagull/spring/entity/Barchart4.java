package luckyweb.seagull.spring.entity;

import java.util.Map;

import javax.persistence.Entity;

@Entity(name="barchart3")
public class Barchart4 implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String type;
	private String stack;
	private Map itemStyle;
	private String name;
	private double[] data;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	public Map getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(Map itemStyle) {
		this.itemStyle = itemStyle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}

}
