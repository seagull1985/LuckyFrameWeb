package luckyweb.seagull.spring.entity;

import java.util.Map;

import javax.persistence.Entity;

@Entity(name="barchart3")
public class Piechart5 implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String type;
	private String center[];
	private String radius;
	private String x;
	private String itemStyle;
	private Map markPoint;
	private Map markLine;
	private String name;
	private double[] data;

}
