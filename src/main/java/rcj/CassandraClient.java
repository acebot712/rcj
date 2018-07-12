package rcj;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraClient {


	public String search(String cacheKey)
	{

		String serverIP = "127.0.0.1";
		String keyspace = "demodb";

		Cluster cluster = Cluster.builder()
		  .addContactPoints(serverIP)
		  .build();

		Session session = cluster.connect(keyspace);

		// ======================connected to cluster=====================
	   
	   String cqlStatement = "SELECT * FROM users WHERE user_id='"+cacheKey+"'";
	   String rowValues = null;
	   for(Row row: session.execute(cqlStatement)){
		   rowValues = row.toString();
		   //System.out.println(rowValues);
	   }
	   return rowValues;
	}
}
