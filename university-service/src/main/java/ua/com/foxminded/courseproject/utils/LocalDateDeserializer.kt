package ua.com.foxminded.courseproject.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDate {
        val node = p.codec.readTree<JsonNode>(p)
        val dateString = node.get("\$date").asText()
        val epochMillis = Instant.parse(dateString).toEpochMilli()
        return LocalDate.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneOffset.UTC)
    }
}