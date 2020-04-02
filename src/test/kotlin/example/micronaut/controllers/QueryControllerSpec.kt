package example.micronaut.controllers

import example.micronaut.models.NewQueryForm
import example.micronaut.models.Query
import io.kotlintest.shouldThrow
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.Duration
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object QueryControllerSpec : Spek({

    describe("QueryController Suite") {
        var embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        var client: HttpClient = HttpClient.create(embeddedServer.url)
        val testUUID = UUID.randomUUID()

        it("test POST /query creates a uuid") {
            val queryForm = NewQueryForm(query = "SELECT * FROM tbl")
            val request = HttpRequest.POST("/query", queryForm)
            val rsp: HttpResponse<Query> = client.toBlocking().exchange(request, Query::class.java)
            assertEquals(HttpStatus.CREATED, rsp.status)
            assertNotNull(rsp.body())
        }

        it("test GET /query returns 404 for query not found") {
            val rsp = shouldThrow<HttpClientResponseException> {
                client.toBlocking().exchange<Any>("/query/$testUUID")
            }.response
            assertEquals(HttpStatus.NOT_FOUND, rsp.status)
        }

        it("test GET /query returns a query") {
            val query = NewQueryForm(query = "SELECT * FROM tbl")
            val request = HttpRequest.POST("/query", query)
            val rsp: HttpResponse<Query> = client.toBlocking().exchange(request, Query::class.java)
            assertEquals(HttpStatus.CREATED, rsp.status)
            assertNotNull(rsp.body())
            val uuid = rsp.body()!!.uuid
            val rsp2 = client.toBlocking().exchange("/query/$uuid", Query::class.java)
            assertEquals(HttpStatus.OK, rsp2.status)
            assertNotNull(rsp2.body())
            assertEquals(rsp2.body()!!.query, query.query)
        }


        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})