package rcj;
import java.util.Scanner;

import redis.clients.jedis.Jedis;

public class RedisJava {
	
	public static void main(String args[]) throws Exception{
		try {
			// CONNECTION INITIATE
			String host = "localhost";
			// The access URL is displayed on the console.
			int port = 6379;
			Scanner sc = new Scanner(System.in);
			
			Jedis jedis = new Jedis(host,port);
			System.out.println("Server ping: "+jedis.ping());
			System.out.println("Successful connection");
			
			// CONNECTION DONE
			
			// TAKE USER INPUT
			System.out.println("Enter key to be searched for:\t");
			String cacheKey = sc.next(); //Here key is user_id
			// jedis.set(cacheKey,"cahed value"); //delete this line later after Cassandra integration
			jedis.expire(cacheKey, 5); // 5 seconds TTL used for key
			System.out.println("TTL:" + jedis.ttl(cacheKey));
			// Thread.sleep(6000);
			if(jedis.get(cacheKey) != null){
				System.out.println("Value retrieved:" + jedis.get(cacheKey));
			}else{
				
				// THIS IS WERE CASSANDRA COMES IN
				System.out.println("Cache timed out. Requesting data from Cassandra DB...");
				CassandraClient client = new CassandraClient();
				String temp = client.search(cacheKey);
				if(temp != null){
					jedis.set(cacheKey, temp);
					System.out.println(temp);
				}else{
					System.out.println("No such user exists with the given user ID");
				}
				
			}


			sc.close();
			jedis.quit();
			jedis.close();
			} catch (Exception e) {
			     e.printStackTrace();
			}
	}

}
