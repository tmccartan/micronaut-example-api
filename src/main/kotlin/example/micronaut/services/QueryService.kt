package example.micronaut.services

import example.micronaut.models.NewQueryForm
import example.micronaut.models.Query
import java.util.*

interface QueryService {
    fun get(uuid: UUID): Query?
    fun getAll(): List<UUID>
    fun create(queryForm: NewQueryForm): Query
}