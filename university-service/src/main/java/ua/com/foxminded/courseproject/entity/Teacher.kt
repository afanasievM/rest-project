package ua.com.foxminded.courseproject.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import ua.com.foxminded.courseproject.utils.LocalDateDeserializer
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@Document(collection = "teachers")
data class Teacher(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    @JsonProperty("_id")
    override var id: UUID? = UUID.randomUUID(),

    @Field("firstname")
    @JsonProperty("firstname")
    override var firstName: String? = null,

    @Field("lastname")
    @JsonProperty("lastname")
    override var lastName: String? = null,

    @Field("birthday")
    @JsonProperty("birthday")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    override var birthDay: LocalDate? = null,

    @Field("degree")
    @JsonProperty("degree")
    var degree: String? = null,

    @Field("salary")
    @JsonProperty("salary")
    var salary: Int? = null,

    @Field("first_date")
    @JsonProperty("first_date")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    var firstDay: LocalDate? = null,

    @Field("rank")
    @JsonProperty("rank")
    var rank: String? = null,

    @Field("title")
    @JsonProperty("title")
    var title: String? = null
) : Person()

