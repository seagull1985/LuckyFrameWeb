package luckyweb.seagull.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlLib {
    // 驱动程序名
	public static String driver = "com.mysql.jdbc.Driver";

    // URL指向要访问的数据库名zentao
    private static String url = "jdbc:mysql://10.211.19.75:3308/zentao?zeroDateTimeBehavior=convertToNull";

    // MySQL配置时的用户名
    private static String user = "root"; 

    // MySQL配置时的密码
    private static String password = "";
    
    public String[][] querydata(String proid){
    	String [][]results=null;
    	try { 
            // 加载驱动程序
            Class.forName(driver);

            // 连续数据库
            Connection conn = DriverManager.getConnection(url, user, password);

            if(!conn.isClosed()) 
             System.out.println("Succeeded connecting to the Database!");

            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            
            // 结果集
            ResultSet rscount = statement.executeQuery("SELECT COUNT(*) as rowCount FROM zt_task  a LEFT JOIN zt_project b on  a.project=b.id  "
            		+ "where b.status = 'done' and a.status = 'done' and a.project = "+proid);
            rscount.next();
            int rowCount = rscount.getInt("rowCount");
			if (rowCount != 0) {
				// 要执行的SQL语句
				String sql = "select aa.id,aa.project,aa.projectname,aa.name,aa.assignedDate,aa.estimate,aa.consumed,c.realname,aa.finishedBy,aa.deadline "
						+ "from (select a.id,a.project,b.name as projectname,a.name,a.assignedDate,a.estimate,a.consumed,a.finishedBy,a.deadline "
						+ "from zt_task a LEFT JOIN zt_project b on  a.project=b.id  where b.status = 'done' and a.status = 'done' and a.project = "
						+ proid + ") aa " + "LEFT JOIN zt_user c on c.account=aa.finishedBy";

				// 结果集
				ResultSet rs = statement.executeQuery(sql);

				results = new String[rowCount][10];
				while (rs.next()) {
					results[rs.getRow() - 1][0] = rs.getString("id");
					results[rs.getRow() - 1][1] = rs.getString("project");
					results[rs.getRow() - 1][2] = rs.getString("projectname");
					results[rs.getRow() - 1][3] = rs.getString("name");
					results[rs.getRow() - 1][4] = rs.getString("assignedDate");
					results[rs.getRow() - 1][5] = rs.getString("estimate");
					results[rs.getRow() - 1][6] = rs.getString("consumed");
					results[rs.getRow() - 1][7] = rs.getString("realname");
					results[rs.getRow() - 1][8] = rs.getString("finishedBy");
					results[rs.getRow() - 1][9] = rs.getString("deadline");
				}
				 rs.close();
			}           
            conn.close();
            return results;
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
            return results;
           } catch(SQLException e) {
            e.printStackTrace();
            return results;
           } catch(Exception e) {
            e.printStackTrace();
            return results;
           }
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MysqlLib aaa=new MysqlLib();
		String [][]aaaq = aaa.querydata("87");
		System.out.println(aaaq.length);
	}

}
