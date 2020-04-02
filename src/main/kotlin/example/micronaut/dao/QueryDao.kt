package example.micronaut.dao

import example.micronaut.models.Query
import java.util.*

interface QueryDao {
    fun create(query: String): Query
    fun get(uuid: UUID): Query?
    fun getAll(): List<UUID>
    fun update(uuid: UUID, query: Query): Query
}