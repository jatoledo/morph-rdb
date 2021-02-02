package es.upm.fi.dia.oeg.morph.r2rml.rdb.yaml

import java.sql.DriverManager
import java.sql.Connection



object connect {
	def main(args: Array[String]) {


		// there's probably a better way to do this

		var connection:Connection = null
				try {
					// make the connection

					val ymlData = new YmlDataInterpreter();
					val dataConn = ymlData.interpretYaml().getDataSource();


					connection = DriverManager.getConnection(dataConn.getUrl, dataConn.getUsername, dataConn.getPassword)

							// create the statement, and run the select query
							val statement = connection.createStatement()
							val resultSet = statement.executeQuery("SELECT * FROM student")
							while ( resultSet.next() ) {
								val host = resultSet.getString("name")
										val user = resultSet.getString("sport")
										println("host, user = " + host + ", " + user)
							}
				} catch {
				case e => e.printStackTrace
				}
	connection.close()

	}
}