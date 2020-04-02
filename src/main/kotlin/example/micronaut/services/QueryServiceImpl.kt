package example.micronaut.services

import example.micronaut.dao.QueryDao
import example.micronaut.models.NewQueryForm
import example.micronaut.models.Query
import java.util.*
import javax.inject.Singleton


@Singleton
class QueryServiceImpl(private val queryDao: QueryDao,
                       private val resolverServiceImpl: ResolverServiceImpl): QueryService {

    override fun create(queryForm: NewQueryForm): Query {
        val query =  queryDao.create(queryForm.query)
        resolverServiceImpl.add(query.uuid!!)
        return query
    }

    override fun get(uuid: UUID): Query? {
        return queryDao.get(uuid)
    }

    override fun getAll(): List<UUID> {
        return queryDao.getAll()
    }
}