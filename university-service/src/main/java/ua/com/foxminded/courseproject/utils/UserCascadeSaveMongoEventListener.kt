package ua.com.foxminded.courseproject.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent


class UserCascadeSaveMongoEventListener : AbstractMongoEventListener<Any?>() {
    @Autowired
    private val mongoOperations: MongoOperations? = null

    override fun onBeforeConvert(event: BeforeConvertEvent<Any?>) {
        println("EVENT")
    }
}