package example.micronaut.services

import example.micronaut.dao.QueryDao
import example.micronaut.models.Query
import io.micronaut.scheduling.annotation.Scheduled
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Singleton


@Singleton
class ResolverServiceImpl(private val queryDao: QueryDao){

    private val queue = ConcurrentLinkedQueue<UUID>()

    fun add(uuid: UUID){
        queue.add(uuid)
    }

    @Scheduled(fixedDelay = "20s")
    fun resolve() {
        if(queue.peek() != null) {
            val uuid = queue.poll()
            val query = queryDao.get(uuid)!!
            val resolvedQuery = Query(query = query.query, uuid = query.uuid, output = "Resolved By Task")
            queryDao.update(query.uuid!!, resolvedQuery)
        }
    }
}