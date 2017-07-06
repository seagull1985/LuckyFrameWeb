package luckyweb.seagull.spring.entity;

import java.util.List;

import javax.persistence.Entity;

@Entity(name="pageresultforbootstrap")
public class PageResultForBootstrap implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<ProjectCase> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<ProjectCase> getRows() {
		return rows;
	}
	public void setRows(List<ProjectCase> rows) {
		this.rows = rows;
	}


}
