# Redis with Cassandra - Implementation

Here we look at an implementation of Redis as an in-memory dynamic cache for data storage with Cassandra DB being used as main solution for storage persistance. Here Redis and Cassandra together fulfill the CAP theorem and provide a reliable, scalable and efficient data storage and retreival system. For Redis uses dynamic caching, bloc replacement policies are based on time to live.

## Functional Requirements

1. Some records are inserted in Cassandra DB
2. Java code queries the cache (Redis) for a record.
3. Initial State: Redis can’t give you that data.
          3.1 Cassandra connection established to get the data item (record) directly.
          3.2 Data item placed temporarily in Redis
4. Before timeout in Redis for that data item, query the Redis and make sure to find that record in Redis.
5. Once timeout takes place for that data item in Redis, Step 3 continues.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
1. Running Cassandra DB instance
2. Running Redis instance
3. JBoss Developer Studio or Eclipse or any other similar IDEs
```

### Installing

Using cql shell we create the keyspace **demodb**(schema for RDBMS):-

```
CREATE KEYSPACE demodb WITH replication={
'class':'SimpleStrategy',
'replication_factor':'3'
};
use demodb;
```

We then define a table **users** in **demodb** as follows:-

```
CREATE TABLE users (
    user_id text PRIMARY KEY,
    user_name text
);
```
Next we insert some dummy data into into the users table.

## Running the tests

//pic 1
Say we want to retreive data with user_id = 100
//pic2
It fails to retreive data from the Redis Cache in the first try and tries to fetch data from Cassandra DB. We do experience some lag in the data retreival process.
It then stores the result in Redis Cache with a Time-To-Live (TTL) = 5 seconds.
//pic3
When we try to retreive the same data again it returns the result relatively at a faster rate (now that it is the Redis cache)

### Preventing common performance issues

Choose TTL through prior experimentation for real implementation. Low TTL will not provide the benefits of the cache and a high TTL may lead to exceeding the in-memory storage capacity.

## Deployment

The model has been tested on localhost and may be extended to Optum OpenShift platform to include routes to Cassandra and Redis instances.

## Authors

* **Sravan Kolichala** - _Supervisor_
* **Abhijoy Sarkar**
