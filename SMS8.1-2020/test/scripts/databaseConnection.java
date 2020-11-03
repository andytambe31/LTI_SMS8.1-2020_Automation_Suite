package scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseConnection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		String query = "SELECT CountryName FROM Country";

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://OHKHKD12\\SMSSQL2,1436;database=SMS8QARSMK;integratedSecurity=true;"; // integratedSecurity=true;
		Connection con = DriverManager.getConnection(connectionUrl);// ,"sms81qc","81smsqc@81smsqc");

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet
		ResultSet rs = stmt.executeQuery(query);

		// While Loop to iterate through all data and print results
		// System. out.println(" |ContractNumber |UnitNumber |TransactionDate");
		int i = 1;
		while (rs.next()) {
			String contractNumber = rs.getString(1);
			// String unitNumber = rs.getString(2);
			// String transactionDate = rs.getString(3);
			System.out.println(i + "  |" + contractNumber);// +"\t\t|"+unitNumber+"\t\t|"+transactionDate);
			i++;
		}
		// closing DB Connection
		con.close();

	}

}
