package luckyweb.seagull.spring.entity;

import java.util.Map;

import javax.persistence.Entity;

@Entity(name="barchart3")
public class Barchart3 implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String type;
	private Map markPoint;
	private Map markLine;
	private String name;
	private double[] data;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map getMarkPoint() {
		return markPoint;
	}
	public void setMarkPoint(Map markPoint) {
		this.markPoint = markPoint;
	}
	public Map getMarkLine() {
		return markLine;
	}
	public void setMarkLine(Map markLine) {
		this.markLine = markLine;
	}
}
