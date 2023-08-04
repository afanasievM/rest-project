package ua.com.foxminded.courseproject.mapper

import org.bson.Document
import org.springframework.stereotype.Component
import ua.com.foxminded.courseproject.dto.SubjectDto
import ua.com.foxminded.courseproject.entity.Subject

@Component
class SubjectMapper : Mapper<SubjectDto?, Subject?, Document> {
    override fun toDto(entity: Subject?): SubjectDto? {
        if (entity == null) {
            return null
        }
        val dto = SubjectDto()
        dto.id = entity.id
        dto.name = entity.name
        return dto
    }

    override fun toEntity(dto: SubjectDto?): Subject? {
        if (dto == null) {
            return null
        }
        val entity = Subject()
        entity.id = dto.id
        entity.name = dto.name
        return entity
    }

    override fun documentToEntity(doc: Document): Subject? {
        TODO("Not yet implemented")
    }

    override fun entityToDocument(entity: Subject?): SubjectDto? {
        TODO("Not yet implemented")
    }


}