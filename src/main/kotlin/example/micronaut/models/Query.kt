package example.micronaut.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class Query  @JsonCreator
constructor(@param:JsonProperty("query") val query: String,
            @param:JsonProperty("uuid") val uuid: UUID?,
            @param:JsonProperty("output") val output: String?)