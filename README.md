# Micronaut Sample API

A sample app using micronaut framework and kotlin

To test

```./gradlew test```


Running locally

```./gradlew run```


Example curls

Create a query to be resolved

```curl -d '{"query": "select * from tbl"}' -H "Content-Type: application/json" -X POST http://localhost:8080/query```

Get a list of all queries received

```curl "http://localhost:8080/query" |jq```

Get a specific query

```curl "http://localhost:8080/query/435069a8-f1e6-412a-b1e1-0082366ab12a" |jq```


A scheduled task is run every 20s to take something off the queue and try resolve it, which is
simulating talking to a BigDB system like Presto/Spark. Implementing the ResolverImpl using SQS and
the InMemoryDB using DynamoDB would be recommend