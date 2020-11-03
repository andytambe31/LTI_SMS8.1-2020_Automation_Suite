package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryRunner {

	public String[][] runQuery(String query, String dbName, String serverLink)
			throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		String connectionUrl = "jdbc:sqlserver:" + serverLink + ";database=" + dbName + ";integratedSecurity=true;";
		Connection con = DriverManager.getConnection(connectionUrl);//, "sms81qc", "81smsqc@81smsqc");

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet
		ResultSet rs = stmt.executeQuery(query);

		// Row
		int j = 0;
		while (rs.next()) {
			j++;
		}

		// Return matrix
		String[][] retArr = new String[j][rs.getMetaData().getColumnCount()];

		j = 0;
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			List ls = new ArrayList();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				retArr[j][i - 1] = rs.getString(i);
			}
			j++;

		}

		// closing DB Connection
		con.close();
		return retArr;

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		QueryRunner qr = new QueryRunner();
		qr.runQuery(null, null, null);
	}

	

}
