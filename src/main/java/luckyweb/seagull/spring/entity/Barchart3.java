package luckyweb.seagull.spring.entity;

import java.util.Map;

import javax.persistence.Entity;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
