package example.micronaut.controllers

import example.micronaut.models.NewQueryForm
import example.micronaut.models.Query
import example.micronaut.services.QueryService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier
import javax.inject.Named
import javax.validation.Valid

@Controller("/query")
class QueryController(
        private val queryService: QueryService,
        @Named(TaskExecutors.IO) private val executor: ExecutorService
) {

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): CompletableFuture<List<UUID>> {
        return CompletableFuture.supplyAsync(
            Supplier {
                queryService.getAll()
            }, executor
        )
    }
    @Get("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@Valid uuid: String): CompletableFuture<Query?>{
        return CompletableFuture.supplyAsync(
            Supplier {
                queryService.get(UUID.fromString(uuid))
            }, executor
        )
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun post(@Body @Valid query: NewQueryForm): CompletableFuture<MutableHttpResponse<Query>>{
        return CompletableFuture.supplyAsync(
            Supplier {
                HttpResponse.created(queryService.create(query))
            }, executor
        )
    }
}