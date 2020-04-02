package example.micronaut.dao

import example.micronaut.models.Query
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
class InMemoryQueryDao: QueryDao {

    private val inMemoryDB: ConcurrentHashMap<String, Query> = ConcurrentHashMap<String, Query>()

    override fun create(query: String): Query {
        val newUUID = UUID.randomUUID().toString()
        inMemoryDB[newUUID] = Query(query, uuid = UUID.fromString(newUUID), output = null)
        return inMemoryDB[newUUID]!!
    }

    override fun get(uuid: UUID): Query? {
        return inMemoryDB[uuid.toString()]
    }

    override fun getAll(): List<UUID> {
        return inMemoryDB.keys().asSequence().map { key -> UUID.fromString(key) }.toList()
    }
    override fun update(uuid: UUID, query: Query): Query {
        inMemoryDB[uuid.toString()] = query
        return query
    }
}